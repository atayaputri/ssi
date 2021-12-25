jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MFSHMService } from '../service/mfshm.service';
import { IMFSHM, MFSHM } from '../mfshm.model';
import { IMFSKS } from 'app/entities/mfsks/mfsks.model';
import { MFSKSService } from 'app/entities/mfsks/service/mfsks.service';

import { MFSHMUpdateComponent } from './mfshm-update.component';

describe('MFSHM Management Update Component', () => {
  let comp: MFSHMUpdateComponent;
  let fixture: ComponentFixture<MFSHMUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mFSHMService: MFSHMService;
  let mFSKSService: MFSKSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MFSHMUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(MFSHMUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MFSHMUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mFSHMService = TestBed.inject(MFSHMService);
    mFSKSService = TestBed.inject(MFSKSService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call MFSKS query and add missing value', () => {
      const mFSHM: IMFSHM = { id: 456 };
      const shsks: IMFSKS = { skno: 79735 };
      mFSHM.shsks = shsks;

      const mFSKSCollection: IMFSKS[] = [{ skno: 48980 }];
      jest.spyOn(mFSKSService, 'query').mockReturnValue(of(new HttpResponse({ body: mFSKSCollection })));
      const additionalMFSKS = [shsks];
      const expectedCollection: IMFSKS[] = [...additionalMFSKS, ...mFSKSCollection];
      jest.spyOn(mFSKSService, 'addMFSKSToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mFSHM });
      comp.ngOnInit();

      expect(mFSKSService.query).toHaveBeenCalled();
      expect(mFSKSService.addMFSKSToCollectionIfMissing).toHaveBeenCalledWith(mFSKSCollection, ...additionalMFSKS);
      expect(comp.mFSKSSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mFSHM: IMFSHM = { id: 456 };
      const shsks: IMFSKS = { skno: 51256 };
      mFSHM.shsks = shsks;

      activatedRoute.data = of({ mFSHM });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(mFSHM));
      expect(comp.mFSKSSharedCollection).toContain(shsks);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MFSHM>>();
      const mFSHM = { id: 123 };
      jest.spyOn(mFSHMService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mFSHM });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mFSHM }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(mFSHMService.update).toHaveBeenCalledWith(mFSHM);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MFSHM>>();
      const mFSHM = new MFSHM();
      jest.spyOn(mFSHMService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mFSHM });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mFSHM }));
      saveSubject.complete();

      // THEN
      expect(mFSHMService.create).toHaveBeenCalledWith(mFSHM);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MFSHM>>();
      const mFSHM = { id: 123 };
      jest.spyOn(mFSHMService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mFSHM });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mFSHMService.update).toHaveBeenCalledWith(mFSHM);
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
  });
});
