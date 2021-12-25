jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TBKABService } from '../service/tbkab.service';
import { ITBKAB, TBKAB } from '../tbkab.model';
import { ITBPROV } from 'app/entities/tbprov/tbprov.model';
import { TBPROVService } from 'app/entities/tbprov/service/tbprov.service';

import { TBKABUpdateComponent } from './tbkab-update.component';

describe('TBKAB Management Update Component', () => {
  let comp: TBKABUpdateComponent;
  let fixture: ComponentFixture<TBKABUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tBKABService: TBKABService;
  let tBPROVService: TBPROVService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBKABUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TBKABUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBKABUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tBKABService = TestBed.inject(TBKABService);
    tBPROVService = TestBed.inject(TBPROVService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TBPROV query and add missing value', () => {
      const tBKAB: ITBKAB = { kabcod: 'CBA' };
      const kabprov: ITBPROV = { provcod: 'bf5b' };
      tBKAB.kabprov = kabprov;

      const tBPROVCollection: ITBPROV[] = [{ provcod: '60bb' }];
      jest.spyOn(tBPROVService, 'query').mockReturnValue(of(new HttpResponse({ body: tBPROVCollection })));
      const additionalTBPROVS = [kabprov];
      const expectedCollection: ITBPROV[] = [...additionalTBPROVS, ...tBPROVCollection];
      jest.spyOn(tBPROVService, 'addTBPROVToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tBKAB });
      comp.ngOnInit();

      expect(tBPROVService.query).toHaveBeenCalled();
      expect(tBPROVService.addTBPROVToCollectionIfMissing).toHaveBeenCalledWith(tBPROVCollection, ...additionalTBPROVS);
      expect(comp.tBPROVSSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tBKAB: ITBKAB = { kabcod: 'CBA' };
      const kabprov: ITBPROV = { provcod: 'f45e' };
      tBKAB.kabprov = kabprov;

      activatedRoute.data = of({ tBKAB });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tBKAB));
      expect(comp.tBPROVSSharedCollection).toContain(kabprov);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBKAB>>();
      const tBKAB = { kabcod: 'ABC' };
      jest.spyOn(tBKABService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBKAB });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBKAB }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tBKABService.update).toHaveBeenCalledWith(tBKAB);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBKAB>>();
      const tBKAB = new TBKAB();
      jest.spyOn(tBKABService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBKAB });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBKAB }));
      saveSubject.complete();

      // THEN
      expect(tBKABService.create).toHaveBeenCalledWith(tBKAB);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBKAB>>();
      const tBKAB = { kabcod: 'ABC' };
      jest.spyOn(tBKABService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBKAB });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tBKABService.update).toHaveBeenCalledWith(tBKAB);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTBPROVByProvcod', () => {
      it('Should return tracked TBPROV primary key', () => {
        const entity = { provcod: 'ABC' };
        const trackResult = comp.trackTBPROVByProvcod(0, entity);
        expect(trackResult).toEqual(entity.provcod);
      });
    });
  });
});
