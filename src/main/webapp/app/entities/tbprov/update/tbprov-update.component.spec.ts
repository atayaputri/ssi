jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TBPROVService } from '../service/tbprov.service';
import { ITBPROV, TBPROV } from '../tbprov.model';
import { ITBNEG } from 'app/entities/tbneg/tbneg.model';
import { TBNEGService } from 'app/entities/tbneg/service/tbneg.service';

import { TBPROVUpdateComponent } from './tbprov-update.component';

describe('TBPROV Management Update Component', () => {
  let comp: TBPROVUpdateComponent;
  let fixture: ComponentFixture<TBPROVUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tBPROVService: TBPROVService;
  let tBNEGService: TBNEGService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBPROVUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TBPROVUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBPROVUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tBPROVService = TestBed.inject(TBPROVService);
    tBNEGService = TestBed.inject(TBNEGService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TBNEG query and add missing value', () => {
      const tBPROV: ITBPROV = { provcod: 'CBA' };
      const provneg: ITBNEG = { negcod: '437d' };
      tBPROV.provneg = provneg;

      const tBNEGCollection: ITBNEG[] = [{ negcod: 'cb51' }];
      jest.spyOn(tBNEGService, 'query').mockReturnValue(of(new HttpResponse({ body: tBNEGCollection })));
      const additionalTBNEGS = [provneg];
      const expectedCollection: ITBNEG[] = [...additionalTBNEGS, ...tBNEGCollection];
      jest.spyOn(tBNEGService, 'addTBNEGToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tBPROV });
      comp.ngOnInit();

      expect(tBNEGService.query).toHaveBeenCalled();
      expect(tBNEGService.addTBNEGToCollectionIfMissing).toHaveBeenCalledWith(tBNEGCollection, ...additionalTBNEGS);
      expect(comp.tBNEGSSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tBPROV: ITBPROV = { provcod: 'CBA' };
      const provneg: ITBNEG = { negcod: '43f2' };
      tBPROV.provneg = provneg;

      activatedRoute.data = of({ tBPROV });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tBPROV));
      expect(comp.tBNEGSSharedCollection).toContain(provneg);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBPROV>>();
      const tBPROV = { provcod: 'ABC' };
      jest.spyOn(tBPROVService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBPROV });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBPROV }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tBPROVService.update).toHaveBeenCalledWith(tBPROV);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBPROV>>();
      const tBPROV = new TBPROV();
      jest.spyOn(tBPROVService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBPROV });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBPROV }));
      saveSubject.complete();

      // THEN
      expect(tBPROVService.create).toHaveBeenCalledWith(tBPROV);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBPROV>>();
      const tBPROV = { provcod: 'ABC' };
      jest.spyOn(tBPROVService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBPROV });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tBPROVService.update).toHaveBeenCalledWith(tBPROV);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTBNEGByNegcod', () => {
      it('Should return tracked TBNEG primary key', () => {
        const entity = { negcod: 'ABC' };
        const trackResult = comp.trackTBNEGByNegcod(0, entity);
        expect(trackResult).toEqual(entity.negcod);
      });
    });
  });
});
