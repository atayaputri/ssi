jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TBJNPSService } from '../service/tbjnps.service';
import { ITBJNPS, TBJNPS } from '../tbjnps.model';

import { TBJNPSUpdateComponent } from './tbjnps-update.component';

describe('TBJNPS Management Update Component', () => {
  let comp: TBJNPSUpdateComponent;
  let fixture: ComponentFixture<TBJNPSUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tBJNPSService: TBJNPSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBJNPSUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TBJNPSUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBJNPSUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tBJNPSService = TestBed.inject(TBJNPSService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tBJNPS: ITBJNPS = { jpscod: 'CBA' };

      activatedRoute.data = of({ tBJNPS });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tBJNPS));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBJNPS>>();
      const tBJNPS = { jpscod: 'ABC' };
      jest.spyOn(tBJNPSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBJNPS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBJNPS }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tBJNPSService.update).toHaveBeenCalledWith(tBJNPS);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBJNPS>>();
      const tBJNPS = new TBJNPS();
      jest.spyOn(tBJNPSService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBJNPS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBJNPS }));
      saveSubject.complete();

      // THEN
      expect(tBJNPSService.create).toHaveBeenCalledWith(tBJNPS);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBJNPS>>();
      const tBJNPS = { jpscod: 'ABC' };
      jest.spyOn(tBJNPSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBJNPS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tBJNPSService.update).toHaveBeenCalledWith(tBJNPS);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
