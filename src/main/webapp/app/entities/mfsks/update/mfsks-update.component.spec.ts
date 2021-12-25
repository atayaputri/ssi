jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MFSKSService } from '../service/mfsks.service';
import { IMFSKS, MFSKS } from '../mfsks.model';
import { IMFHDR } from 'app/entities/mfhdr/mfhdr.model';
import { MFHDRService } from 'app/entities/mfhdr/service/mfhdr.service';

import { MFSKSUpdateComponent } from './mfsks-update.component';

describe('MFSKS Management Update Component', () => {
  let comp: MFSKSUpdateComponent;
  let fixture: ComponentFixture<MFSKSUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mFSKSService: MFSKSService;
  let mFHDRService: MFHDRService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MFSKSUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(MFSKSUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MFSKSUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mFSKSService = TestBed.inject(MFSKSService);
    mFHDRService = TestBed.inject(MFHDRService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MFHDR query and add missing value', () => {
      const mFSKS: IMFSKS = { skno: 456 };
      const skshdr: IMFHDR = { hdno: 46104 };
      mFSKS.skshdr = skshdr;

      const mFHDRCollection: IMFHDR[] = [{ hdno: 51518 }];
      jest.spyOn(mFHDRService, 'query').mockReturnValue(of(new HttpResponse({ body: mFHDRCollection })));
      const additionalMFHDRS = [skshdr];
      const expectedCollection: IMFHDR[] = [...additionalMFHDRS, ...mFHDRCollection];
      jest.spyOn(mFHDRService, 'addMFHDRToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mFSKS });
      comp.ngOnInit();

      expect(mFHDRService.query).toHaveBeenCalled();
      expect(mFHDRService.addMFHDRToCollectionIfMissing).toHaveBeenCalledWith(mFHDRCollection, ...additionalMFHDRS);
      expect(comp.mFHDRSSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mFSKS: IMFSKS = { skno: 456 };
      const skshdr: IMFHDR = { hdno: 48422 };
      mFSKS.skshdr = skshdr;

      activatedRoute.data = of({ mFSKS });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(mFSKS));
      expect(comp.mFHDRSSharedCollection).toContain(skshdr);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MFSKS>>();
      const mFSKS = { skno: 123 };
      jest.spyOn(mFSKSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mFSKS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mFSKS }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(mFSKSService.update).toHaveBeenCalledWith(mFSKS);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MFSKS>>();
      const mFSKS = new MFSKS();
      jest.spyOn(mFSKSService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mFSKS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mFSKS }));
      saveSubject.complete();

      // THEN
      expect(mFSKSService.create).toHaveBeenCalledWith(mFSKS);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MFSKS>>();
      const mFSKS = { skno: 123 };
      jest.spyOn(mFSKSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mFSKS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mFSKSService.update).toHaveBeenCalledWith(mFSKS);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackMFHDRByHdno', () => {
      it('Should return tracked MFHDR primary key', () => {
        const entity = { hdno: 123 };
        const trackResult = comp.trackMFHDRByHdno(0, entity);
        expect(trackResult).toEqual(entity.hdno);
      });
    });
  });
});
