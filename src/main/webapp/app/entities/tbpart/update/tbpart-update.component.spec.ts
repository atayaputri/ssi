jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TBPARTService } from '../service/tbpart.service';
import { ITBPART, TBPART } from '../tbpart.model';

import { TBPARTUpdateComponent } from './tbpart-update.component';

describe('TBPART Management Update Component', () => {
  let comp: TBPARTUpdateComponent;
  let fixture: ComponentFixture<TBPARTUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tBPARTService: TBPARTService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBPARTUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TBPARTUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBPARTUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tBPARTService = TestBed.inject(TBPARTService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tBPART: ITBPART = { tpacode: 'CBA' };

      activatedRoute.data = of({ tBPART });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tBPART));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBPART>>();
      const tBPART = { tpacode: 'ABC' };
      jest.spyOn(tBPARTService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBPART });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBPART }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tBPARTService.update).toHaveBeenCalledWith(tBPART);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBPART>>();
      const tBPART = new TBPART();
      jest.spyOn(tBPARTService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBPART });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBPART }));
      saveSubject.complete();

      // THEN
      expect(tBPARTService.create).toHaveBeenCalledWith(tBPART);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBPART>>();
      const tBPART = { tpacode: 'ABC' };
      jest.spyOn(tBPARTService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBPART });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tBPARTService.update).toHaveBeenCalledWith(tBPART);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
