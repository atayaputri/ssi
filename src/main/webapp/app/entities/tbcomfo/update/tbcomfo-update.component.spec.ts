jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TBCOMFOService } from '../service/tbcomfo.service';
import { ITBCOMFO, TBCOMFO } from '../tbcomfo.model';

import { TBCOMFOUpdateComponent } from './tbcomfo-update.component';

describe('TBCOMFO Management Update Component', () => {
  let comp: TBCOMFOUpdateComponent;
  let fixture: ComponentFixture<TBCOMFOUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tBCOMFOService: TBCOMFOService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBCOMFOUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TBCOMFOUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBCOMFOUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tBCOMFOService = TestBed.inject(TBCOMFOService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tBCOMFO: ITBCOMFO = { id: 456 };

      activatedRoute.data = of({ tBCOMFO });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tBCOMFO));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBCOMFO>>();
      const tBCOMFO = { id: 123 };
      jest.spyOn(tBCOMFOService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBCOMFO });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBCOMFO }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tBCOMFOService.update).toHaveBeenCalledWith(tBCOMFO);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBCOMFO>>();
      const tBCOMFO = new TBCOMFO();
      jest.spyOn(tBCOMFOService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBCOMFO });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBCOMFO }));
      saveSubject.complete();

      // THEN
      expect(tBCOMFOService.create).toHaveBeenCalledWith(tBCOMFO);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBCOMFO>>();
      const tBCOMFO = { id: 123 };
      jest.spyOn(tBCOMFOService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBCOMFO });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tBCOMFOService.update).toHaveBeenCalledWith(tBCOMFO);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
