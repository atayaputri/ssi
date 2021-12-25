jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TBBROKERService } from '../service/tbbroker.service';
import { ITBBROKER, TBBROKER } from '../tbbroker.model';

import { TBBROKERUpdateComponent } from './tbbroker-update.component';

describe('TBBROKER Management Update Component', () => {
  let comp: TBBROKERUpdateComponent;
  let fixture: ComponentFixture<TBBROKERUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tBBROKERService: TBBROKERService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBBROKERUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TBBROKERUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBBROKERUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tBBROKERService = TestBed.inject(TBBROKERService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tBBROKER: ITBBROKER = { brcode: 'CBA' };

      activatedRoute.data = of({ tBBROKER });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tBBROKER));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBBROKER>>();
      const tBBROKER = { brcode: 'ABC' };
      jest.spyOn(tBBROKERService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBBROKER });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBBROKER }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tBBROKERService.update).toHaveBeenCalledWith(tBBROKER);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBBROKER>>();
      const tBBROKER = new TBBROKER();
      jest.spyOn(tBBROKERService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBBROKER });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBBROKER }));
      saveSubject.complete();

      // THEN
      expect(tBBROKERService.create).toHaveBeenCalledWith(tBBROKER);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBBROKER>>();
      const tBBROKER = { brcode: 'ABC' };
      jest.spyOn(tBBROKERService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBBROKER });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tBBROKERService.update).toHaveBeenCalledWith(tBBROKER);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
