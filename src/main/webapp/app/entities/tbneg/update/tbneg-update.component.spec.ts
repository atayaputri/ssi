jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TBNEGService } from '../service/tbneg.service';
import { ITBNEG, TBNEG } from '../tbneg.model';

import { TBNEGUpdateComponent } from './tbneg-update.component';

describe('TBNEG Management Update Component', () => {
  let comp: TBNEGUpdateComponent;
  let fixture: ComponentFixture<TBNEGUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tBNEGService: TBNEGService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBNEGUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TBNEGUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBNEGUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tBNEGService = TestBed.inject(TBNEGService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tBNEG: ITBNEG = { negcod: 'CBA' };

      activatedRoute.data = of({ tBNEG });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tBNEG));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBNEG>>();
      const tBNEG = { negcod: 'ABC' };
      jest.spyOn(tBNEGService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBNEG });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBNEG }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tBNEGService.update).toHaveBeenCalledWith(tBNEG);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBNEG>>();
      const tBNEG = new TBNEG();
      jest.spyOn(tBNEGService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBNEG });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBNEG }));
      saveSubject.complete();

      // THEN
      expect(tBNEGService.create).toHaveBeenCalledWith(tBNEG);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBNEG>>();
      const tBNEG = { negcod: 'ABC' };
      jest.spyOn(tBNEGService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBNEG });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tBNEGService.update).toHaveBeenCalledWith(tBNEG);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
