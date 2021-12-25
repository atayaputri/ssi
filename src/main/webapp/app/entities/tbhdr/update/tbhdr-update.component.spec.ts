jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TBHDRService } from '../service/tbhdr.service';
import { ITBHDR, TBHDR } from '../tbhdr.model';
import { ITBJNPS } from 'app/entities/tbjnps/tbjnps.model';
import { TBJNPSService } from 'app/entities/tbjnps/service/tbjnps.service';

import { TBHDRUpdateComponent } from './tbhdr-update.component';

describe('TBHDR Management Update Component', () => {
  let comp: TBHDRUpdateComponent;
  let fixture: ComponentFixture<TBHDRUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tBHDRService: TBHDRService;
  let tBJNPSService: TBJNPSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBHDRUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TBHDRUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBHDRUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tBHDRService = TestBed.inject(TBHDRService);
    tBJNPSService = TestBed.inject(TBJNPSService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TBJNPS query and add missing value', () => {
      const tBHDR: ITBHDR = { thno: 456 };
      const thjnps: ITBJNPS = { jpscod: 'd' };
      tBHDR.thjnps = thjnps;

      const tBJNPSCollection: ITBJNPS[] = [{ jpscod: '1' }];
      jest.spyOn(tBJNPSService, 'query').mockReturnValue(of(new HttpResponse({ body: tBJNPSCollection })));
      const additionalTBJNPS = [thjnps];
      const expectedCollection: ITBJNPS[] = [...additionalTBJNPS, ...tBJNPSCollection];
      jest.spyOn(tBJNPSService, 'addTBJNPSToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tBHDR });
      comp.ngOnInit();

      expect(tBJNPSService.query).toHaveBeenCalled();
      expect(tBJNPSService.addTBJNPSToCollectionIfMissing).toHaveBeenCalledWith(tBJNPSCollection, ...additionalTBJNPS);
      expect(comp.tBJNPSSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tBHDR: ITBHDR = { thno: 456 };
      const thjnps: ITBJNPS = { jpscod: 'b' };
      tBHDR.thjnps = thjnps;

      activatedRoute.data = of({ tBHDR });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tBHDR));
      expect(comp.tBJNPSSharedCollection).toContain(thjnps);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBHDR>>();
      const tBHDR = { thno: 123 };
      jest.spyOn(tBHDRService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBHDR });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBHDR }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tBHDRService.update).toHaveBeenCalledWith(tBHDR);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBHDR>>();
      const tBHDR = new TBHDR();
      jest.spyOn(tBHDRService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBHDR });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBHDR }));
      saveSubject.complete();

      // THEN
      expect(tBHDRService.create).toHaveBeenCalledWith(tBHDR);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBHDR>>();
      const tBHDR = { thno: 123 };
      jest.spyOn(tBHDRService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBHDR });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tBHDRService.update).toHaveBeenCalledWith(tBHDR);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTBJNPSByJpscod', () => {
      it('Should return tracked TBJNPS primary key', () => {
        const entity = { jpscod: 'ABC' };
        const trackResult = comp.trackTBJNPSByJpscod(0, entity);
        expect(trackResult).toEqual(entity.jpscod);
      });
    });
  });
});
