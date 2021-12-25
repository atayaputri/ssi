jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { TBTYPSService } from '../service/tbtyps.service';
import { ITBTYPS, TBTYPS } from '../tbtyps.model';

import { TBTYPSUpdateComponent } from './tbtyps-update.component';

describe('TBTYPS Management Update Component', () => {
  let comp: TBTYPSUpdateComponent;
  let fixture: ComponentFixture<TBTYPSUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let tBTYPSService: TBTYPSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBTYPSUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(TBTYPSUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBTYPSUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    tBTYPSService = TestBed.inject(TBTYPSService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const tBTYPS: ITBTYPS = { tpscod: 'CBA' };

      activatedRoute.data = of({ tBTYPS });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(tBTYPS));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBTYPS>>();
      const tBTYPS = { tpscod: 'ABC' };
      jest.spyOn(tBTYPSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBTYPS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBTYPS }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(tBTYPSService.update).toHaveBeenCalledWith(tBTYPS);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBTYPS>>();
      const tBTYPS = new TBTYPS();
      jest.spyOn(tBTYPSService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBTYPS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: tBTYPS }));
      saveSubject.complete();

      // THEN
      expect(tBTYPSService.create).toHaveBeenCalledWith(tBTYPS);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TBTYPS>>();
      const tBTYPS = { tpscod: 'ABC' };
      jest.spyOn(tBTYPSService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ tBTYPS });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(tBTYPSService.update).toHaveBeenCalledWith(tBTYPS);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
