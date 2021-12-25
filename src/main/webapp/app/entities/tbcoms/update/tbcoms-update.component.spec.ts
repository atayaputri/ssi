jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TBCOMSService } from '../service/tbcoms.service';
import { ITBCOMS, TBCOMS } from '../tbcoms.model';
import { ITBJNSHM } from 'app/entities/tbjnshm/tbjnshm.model';
import { TBJNSHMService } from 'app/entities/tbjnshm/service/tbjnshm.service';

import { TBCOMSUpdateComponent } from './tbcoms-update.component';

describe('TBCOMS Management Update Component', () => {
  let comp: TBCOMSUpdateComponent;
  let fixture: ComponentFixture<TBCOMSUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tBCOMSService: TBCOMSService;
  let tBJNSHMService: TBJNSHMService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBCOMSUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TBCOMSUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBCOMSUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tBCOMSService = TestBed.inject(TBCOMSService);
    tBJNSHMService = TestBed.inject(TBJNSHMService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TBJNSHM query and add missing value', () => {
      const tBCOMS: ITBCOMS = { cocode: 'CBA' };
      const cojnsh: ITBJNSHM = { jshcod: 'f' };
      tBCOMS.cojnsh = cojnsh;

      const tBJNSHMCollection: ITBJNSHM[] = [{ jshcod: '5' }];
      jest.spyOn(tBJNSHMService, 'query').mockReturnValue(of(new HttpResponse({ body: tBJNSHMCollection })));
      const additionalTBJNSHMS = [cojnsh];
      const expectedCollection: ITBJNSHM[] = [...additionalTBJNSHMS, ...tBJNSHMCollection];
      jest.spyOn(tBJNSHMService, 'addTBJNSHMToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tBCOMS });
      comp.ngOnInit();

      expect(tBJNSHMService.query).toHaveBeenCalled();
      expect(tBJNSHMService.addTBJNSHMToCollectionIfMissing).toHaveBeenCalledWith(tBJNSHMCollection, ...additionalTBJNSHMS);
      expect(comp.tBJNSHMSSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tBCOMS: ITBCOMS = { cocode: 'CBA' };
      const cojnsh: ITBJNSHM = { jshcod: 'c' };
      tBCOMS.cojnsh = cojnsh;

      activatedRoute.data = of({ tBCOMS });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tBCOMS));
      expect(comp.tBJNSHMSSharedCollection).toContain(cojnsh);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBCOMS>>();
      const tBCOMS = { cocode: 'ABC' };
      jest.spyOn(tBCOMSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBCOMS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBCOMS }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tBCOMSService.update).toHaveBeenCalledWith(tBCOMS);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBCOMS>>();
      const tBCOMS = new TBCOMS();
      jest.spyOn(tBCOMSService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBCOMS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBCOMS }));
      saveSubject.complete();

      // THEN
      expect(tBCOMSService.create).toHaveBeenCalledWith(tBCOMS);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBCOMS>>();
      const tBCOMS = { cocode: 'ABC' };
      jest.spyOn(tBCOMSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBCOMS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tBCOMSService.update).toHaveBeenCalledWith(tBCOMS);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTBJNSHMByJshcod', () => {
      it('Should return tracked TBJNSHM primary key', () => {
        const entity = { jshcod: 'ABC' };
        const trackResult = comp.trackTBJNSHMByJshcod(0, entity);
        expect(trackResult).toEqual(entity.jshcod);
      });
    });
  });
});
