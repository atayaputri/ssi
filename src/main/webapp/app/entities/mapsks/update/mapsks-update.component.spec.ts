jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MAPSKSService } from '../service/mapsks.service';
import { IMAPSKS, MAPSKS } from '../mapsks.model';
import { IMFSKS } from 'app/entities/mfsks/mfsks.model';
import { MFSKSService } from 'app/entities/mfsks/service/mfsks.service';
import { IMFHDR } from 'app/entities/mfhdr/mfhdr.model';
import { MFHDRService } from 'app/entities/mfhdr/service/mfhdr.service';

import { MAPSKSUpdateComponent } from './mapsks-update.component';

describe('MAPSKS Management Update Component', () => {
  let comp: MAPSKSUpdateComponent;
  let fixture: ComponentFixture<MAPSKSUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mAPSKSService: MAPSKSService;
  let mFSKSService: MFSKSService;
  let mFHDRService: MFHDRService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MAPSKSUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(MAPSKSUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MAPSKSUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mAPSKSService = TestBed.inject(MAPSKSService);
    mFSKSService = TestBed.inject(MFSKSService);
    mFHDRService = TestBed.inject(MFHDRService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MFSKS query and add missing value', () => {
      const mAPSKS: IMAPSKS = { id: 456 };
      const mskno: IMFSKS = { skno: 16251 };
      mAPSKS.mskno = mskno;

      const mFSKSCollection: IMFSKS[] = [{ skno: 23844 }];
      jest.spyOn(mFSKSService, 'query').mockReturnValue(of(new HttpResponse({ body: mFSKSCollection })));
      const additionalMFSKS = [mskno];
      const expectedCollection: IMFSKS[] = [...additionalMFSKS, ...mFSKSCollection];
      jest.spyOn(mFSKSService, 'addMFSKSToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mAPSKS });
      comp.ngOnInit();

      expect(mFSKSService.query).toHaveBeenCalled();
      expect(mFSKSService.addMFSKSToCollectionIfMissing).toHaveBeenCalledWith(mFSKSCollection, ...additionalMFSKS);
      expect(comp.mFSKSSharedCollection).toEqual(expectedCollection);
    });

    it('Should call MFHDR query and add missing value', () => {
      const mAPSKS: IMAPSKS = { id: 456 };
      const mskohdr: IMFHDR = { hdno: 30220 };
      mAPSKS.mskohdr = mskohdr;
      const mskhdr: IMFHDR = { hdno: 23483 };
      mAPSKS.mskhdr = mskhdr;

      const mFHDRCollection: IMFHDR[] = [{ hdno: 39184 }];
      jest.spyOn(mFHDRService, 'query').mockReturnValue(of(new HttpResponse({ body: mFHDRCollection })));
      const additionalMFHDRS = [mskohdr, mskhdr];
      const expectedCollection: IMFHDR[] = [...additionalMFHDRS, ...mFHDRCollection];
      jest.spyOn(mFHDRService, 'addMFHDRToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mAPSKS });
      comp.ngOnInit();

      expect(mFHDRService.query).toHaveBeenCalled();
      expect(mFHDRService.addMFHDRToCollectionIfMissing).toHaveBeenCalledWith(mFHDRCollection, ...additionalMFHDRS);
      expect(comp.mFHDRSSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mAPSKS: IMAPSKS = { id: 456 };
      const mskno: IMFSKS = { skno: 68810 };
      mAPSKS.mskno = mskno;
      const mskohdr: IMFHDR = { hdno: 57373 };
      mAPSKS.mskohdr = mskohdr;
      const mskhdr: IMFHDR = { hdno: 97568 };
      mAPSKS.mskhdr = mskhdr;

      activatedRoute.data = of({ mAPSKS });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(mAPSKS));
      expect(comp.mFSKSSharedCollection).toContain(mskno);
      expect(comp.mFHDRSSharedCollection).toContain(mskohdr);
      expect(comp.mFHDRSSharedCollection).toContain(mskhdr);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MAPSKS>>();
      const mAPSKS = { id: 123 };
      jest.spyOn(mAPSKSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mAPSKS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mAPSKS }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(mAPSKSService.update).toHaveBeenCalledWith(mAPSKS);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MAPSKS>>();
      const mAPSKS = new MAPSKS();
      jest.spyOn(mAPSKSService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mAPSKS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mAPSKS }));
      saveSubject.complete();

      // THEN
      expect(mAPSKSService.create).toHaveBeenCalledWith(mAPSKS);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MAPSKS>>();
      const mAPSKS = { id: 123 };
      jest.spyOn(mAPSKSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mAPSKS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mAPSKSService.update).toHaveBeenCalledWith(mAPSKS);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackMFSKSBySkno', () => {
      it('Should return tracked MFSKS primary key', () => {
        const entity = { skno: 123 };
        const trackResult = comp.trackMFSKSBySkno(0, entity);
        expect(trackResult).toEqual(entity.skno);
      });
    });

    describe('trackMFHDRByHdno', () => {
      it('Should return tracked MFHDR primary key', () => {
        const entity = { hdno: 123 };
        const trackResult = comp.trackMFHDRByHdno(0, entity);
        expect(trackResult).toEqual(entity.hdno);
      });
    });
  });
});
