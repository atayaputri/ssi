jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TABFEEService } from '../service/tabfee.service';
import { ITABFEE, TABFEE } from '../tabfee.model';
import { ITABJTRX } from 'app/entities/tabjtrx/tabjtrx.model';
import { TABJTRXService } from 'app/entities/tabjtrx/service/tabjtrx.service';

import { TABFEEUpdateComponent } from './tabfee-update.component';

describe('TABFEE Management Update Component', () => {
  let comp: TABFEEUpdateComponent;
  let fixture: ComponentFixture<TABFEEUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tABFEEService: TABFEEService;
  let tABJTRXService: TABJTRXService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TABFEEUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TABFEEUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TABFEEUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tABFEEService = TestBed.inject(TABFEEService);
    tABJTRXService = TestBed.inject(TABJTRXService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TABJTRX query and add missing value', () => {
      const tABFEE: ITABFEE = { id: 456 };
      const fejns: ITABJTRX = { jtjntx: '7' };
      tABFEE.fejns = fejns;

      const tABJTRXCollection: ITABJTRX[] = [{ jtjntx: '8' }];
      jest.spyOn(tABJTRXService, 'query').mockReturnValue(of(new HttpResponse({ body: tABJTRXCollection })));
      const additionalTABJTRXES = [fejns];
      const expectedCollection: ITABJTRX[] = [...additionalTABJTRXES, ...tABJTRXCollection];
      jest.spyOn(tABJTRXService, 'addTABJTRXToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ tABFEE });
      comp.ngOnInit();

      expect(tABJTRXService.query).toHaveBeenCalled();
      expect(tABJTRXService.addTABJTRXToCollectionIfMissing).toHaveBeenCalledWith(tABJTRXCollection, ...additionalTABJTRXES);
      expect(comp.tABJTRXESSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const tABFEE: ITABFEE = { id: 456 };
      const fejns: ITABJTRX = { jtjntx: 'f' };
      tABFEE.fejns = fejns;

      activatedRoute.data = of({ tABFEE });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tABFEE));
      expect(comp.tABJTRXESSharedCollection).toContain(fejns);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TABFEE>>();
      const tABFEE = { id: 123 };
      jest.spyOn(tABFEEService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tABFEE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tABFEE }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tABFEEService.update).toHaveBeenCalledWith(tABFEE);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TABFEE>>();
      const tABFEE = new TABFEE();
      jest.spyOn(tABFEEService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tABFEE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tABFEE }));
      saveSubject.complete();

      // THEN
      expect(tABFEEService.create).toHaveBeenCalledWith(tABFEE);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TABFEE>>();
      const tABFEE = { id: 123 };
      jest.spyOn(tABFEEService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tABFEE });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tABFEEService.update).toHaveBeenCalledWith(tABFEE);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTABJTRXByJtjntx', () => {
      it('Should return tracked TABJTRX primary key', () => {
        const entity = { jtjntx: 'ABC' };
        const trackResult = comp.trackTABJTRXByJtjntx(0, entity);
        expect(trackResult).toEqual(entity.jtjntx);
      });
    });
  });
});
