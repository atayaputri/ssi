jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TBJNSHMService } from '../service/tbjnshm.service';
import { ITBJNSHM, TBJNSHM } from '../tbjnshm.model';

import { TBJNSHMUpdateComponent } from './tbjnshm-update.component';

describe('TBJNSHM Management Update Component', () => {
  let comp: TBJNSHMUpdateComponent;
  let fixture: ComponentFixture<TBJNSHMUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tBJNSHMService: TBJNSHMService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBJNSHMUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TBJNSHMUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBJNSHMUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tBJNSHMService = TestBed.inject(TBJNSHMService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tBJNSHM: ITBJNSHM = { jshcod: 'CBA' };

      activatedRoute.data = of({ tBJNSHM });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tBJNSHM));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBJNSHM>>();
      const tBJNSHM = { jshcod: 'ABC' };
      jest.spyOn(tBJNSHMService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBJNSHM });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBJNSHM }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tBJNSHMService.update).toHaveBeenCalledWith(tBJNSHM);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBJNSHM>>();
      const tBJNSHM = new TBJNSHM();
      jest.spyOn(tBJNSHMService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBJNSHM });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBJNSHM }));
      saveSubject.complete();

      // THEN
      expect(tBJNSHMService.create).toHaveBeenCalledWith(tBJNSHM);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBJNSHM>>();
      const tBJNSHM = { jshcod: 'ABC' };
      jest.spyOn(tBJNSHMService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBJNSHM });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tBJNSHMService.update).toHaveBeenCalledWith(tBJNSHM);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
