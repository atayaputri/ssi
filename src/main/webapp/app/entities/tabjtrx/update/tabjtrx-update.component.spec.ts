jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TABJTRXService } from '../service/tabjtrx.service';
import { ITABJTRX, TABJTRX } from '../tabjtrx.model';

import { TABJTRXUpdateComponent } from './tabjtrx-update.component';

describe('TABJTRX Management Update Component', () => {
  let comp: TABJTRXUpdateComponent;
  let fixture: ComponentFixture<TABJTRXUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tABJTRXService: TABJTRXService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TABJTRXUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TABJTRXUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TABJTRXUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tABJTRXService = TestBed.inject(TABJTRXService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tABJTRX: ITABJTRX = { jtjntx: 'CBA' };

      activatedRoute.data = of({ tABJTRX });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tABJTRX));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TABJTRX>>();
      const tABJTRX = { jtjntx: 'ABC' };
      jest.spyOn(tABJTRXService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tABJTRX });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tABJTRX }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tABJTRXService.update).toHaveBeenCalledWith(tABJTRX);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TABJTRX>>();
      const tABJTRX = new TABJTRX();
      jest.spyOn(tABJTRXService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tABJTRX });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tABJTRX }));
      saveSubject.complete();

      // THEN
      expect(tABJTRXService.create).toHaveBeenCalledWith(tABJTRX);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TABJTRX>>();
      const tABJTRX = { jtjntx: 'ABC' };
      jest.spyOn(tABJTRXService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tABJTRX });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tABJTRXService.update).toHaveBeenCalledWith(tABJTRX);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
