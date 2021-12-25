jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { LISTEMTService } from '../service/listemt.service';
import { ILISTEMT, LISTEMT } from '../listemt.model';

import { LISTEMTUpdateComponent } from './listemt-update.component';

describe('LISTEMT Management Update Component', () => {
  let comp: LISTEMTUpdateComponent;
  let fixture: ComponentFixture<LISTEMTUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let lISTEMTService: LISTEMTService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [LISTEMTUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(LISTEMTUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(LISTEMTUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    lISTEMTService = TestBed.inject(LISTEMTService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const lISTEMT: ILISTEMT = { id: 456 };

      activatedRoute.data = of({ lISTEMT });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(lISTEMT));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LISTEMT>>();
      const lISTEMT = { id: 123 };
      jest.spyOn(lISTEMTService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lISTEMT });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lISTEMT }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(lISTEMTService.update).toHaveBeenCalledWith(lISTEMT);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LISTEMT>>();
      const lISTEMT = new LISTEMT();
      jest.spyOn(lISTEMTService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lISTEMT });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: lISTEMT }));
      saveSubject.complete();

      // THEN
      expect(lISTEMTService.create).toHaveBeenCalledWith(lISTEMT);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<LISTEMT>>();
      const lISTEMT = { id: 123 };
      jest.spyOn(lISTEMTService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ lISTEMT });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(lISTEMTService.update).toHaveBeenCalledWith(lISTEMT);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
