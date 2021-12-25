jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { MFHDRService } from '../service/mfhdr.service';
import { IMFHDR, MFHDR } from '../mfhdr.model';
import { ITBKAB } from 'app/entities/tbkab/tbkab.model';
import { TBKABService } from 'app/entities/tbkab/service/tbkab.service';
import { ITBPROV } from 'app/entities/tbprov/tbprov.model';
import { TBPROVService } from 'app/entities/tbprov/service/tbprov.service';
import { ITBNEG } from 'app/entities/tbneg/tbneg.model';
import { TBNEGService } from 'app/entities/tbneg/service/tbneg.service';
import { ITBJNPS } from 'app/entities/tbjnps/tbjnps.model';
import { TBJNPSService } from 'app/entities/tbjnps/service/tbjnps.service';
import { ITBTYPS } from 'app/entities/tbtyps/tbtyps.model';
import { TBTYPSService } from 'app/entities/tbtyps/service/tbtyps.service';

import { MFHDRUpdateComponent } from './mfhdr-update.component';

describe('MFHDR Management Update Component', () => {
  let comp: MFHDRUpdateComponent;
  let fixture: ComponentFixture<MFHDRUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let mFHDRService: MFHDRService;
  let tBKABService: TBKABService;
  let tBPROVService: TBPROVService;
  let tBNEGService: TBNEGService;
  let tBJNPSService: TBJNPSService;
  let tBTYPSService: TBTYPSService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MFHDRUpdateComponent],
      providers: [FormBuilder, ActivatedRoute],
    })
      .overrideTemplate(MFHDRUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MFHDRUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    mFHDRService = TestBed.inject(MFHDRService);
    tBKABService = TestBed.inject(TBKABService);
    tBPROVService = TestBed.inject(TBPROVService);
    tBNEGService = TestBed.inject(TBNEGService);
    tBJNPSService = TestBed.inject(TBJNPSService);
    tBTYPSService = TestBed.inject(TBTYPSService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call TBKAB query and add missing value', () => {
      const mFHDR: IMFHDR = { hdno: 456 };
      const hdkota: ITBKAB = { kabcod: 'f12b' };
      mFHDR.hdkota = hdkota;

      const tBKABCollection: ITBKAB[] = [{ kabcod: 'e1b1' }];
      jest.spyOn(tBKABService, 'query').mockReturnValue(of(new HttpResponse({ body: tBKABCollection })));
      const additionalTBKABS = [hdkota];
      const expectedCollection: ITBKAB[] = [...additionalTBKABS, ...tBKABCollection];
      jest.spyOn(tBKABService, 'addTBKABToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mFHDR });
      comp.ngOnInit();

      expect(tBKABService.query).toHaveBeenCalled();
      expect(tBKABService.addTBKABToCollectionIfMissing).toHaveBeenCalledWith(tBKABCollection, ...additionalTBKABS);
      expect(comp.tBKABSSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TBPROV query and add missing value', () => {
      const mFHDR: IMFHDR = { hdno: 456 };
      const hdprov: ITBPROV = { provcod: 'f509' };
      mFHDR.hdprov = hdprov;

      const tBPROVCollection: ITBPROV[] = [{ provcod: 'ea39' }];
      jest.spyOn(tBPROVService, 'query').mockReturnValue(of(new HttpResponse({ body: tBPROVCollection })));
      const additionalTBPROVS = [hdprov];
      const expectedCollection: ITBPROV[] = [...additionalTBPROVS, ...tBPROVCollection];
      jest.spyOn(tBPROVService, 'addTBPROVToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mFHDR });
      comp.ngOnInit();

      expect(tBPROVService.query).toHaveBeenCalled();
      expect(tBPROVService.addTBPROVToCollectionIfMissing).toHaveBeenCalledWith(tBPROVCollection, ...additionalTBPROVS);
      expect(comp.tBPROVSSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TBNEG query and add missing value', () => {
      const mFHDR: IMFHDR = { hdno: 456 };
      const hdneg: ITBNEG = { negcod: '51af' };
      mFHDR.hdneg = hdneg;

      const tBNEGCollection: ITBNEG[] = [{ negcod: '3ef6' }];
      jest.spyOn(tBNEGService, 'query').mockReturnValue(of(new HttpResponse({ body: tBNEGCollection })));
      const additionalTBNEGS = [hdneg];
      const expectedCollection: ITBNEG[] = [...additionalTBNEGS, ...tBNEGCollection];
      jest.spyOn(tBNEGService, 'addTBNEGToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mFHDR });
      comp.ngOnInit();

      expect(tBNEGService.query).toHaveBeenCalled();
      expect(tBNEGService.addTBNEGToCollectionIfMissing).toHaveBeenCalledWith(tBNEGCollection, ...additionalTBNEGS);
      expect(comp.tBNEGSSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TBJNPS query and add missing value', () => {
      const mFHDR: IMFHDR = { hdno: 456 };
      const hdjnps: ITBJNPS = { jpscod: '2' };
      mFHDR.hdjnps = hdjnps;

      const tBJNPSCollection: ITBJNPS[] = [{ jpscod: '5' }];
      jest.spyOn(tBJNPSService, 'query').mockReturnValue(of(new HttpResponse({ body: tBJNPSCollection })));
      const additionalTBJNPS = [hdjnps];
      const expectedCollection: ITBJNPS[] = [...additionalTBJNPS, ...tBJNPSCollection];
      jest.spyOn(tBJNPSService, 'addTBJNPSToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mFHDR });
      comp.ngOnInit();

      expect(tBJNPSService.query).toHaveBeenCalled();
      expect(tBJNPSService.addTBJNPSToCollectionIfMissing).toHaveBeenCalledWith(tBJNPSCollection, ...additionalTBJNPS);
      expect(comp.tBJNPSSharedCollection).toEqual(expectedCollection);
    });

    it('Should call TBTYPS query and add missing value', () => {
      const mFHDR: IMFHDR = { hdno: 456 };
      const hdtyps: ITBTYPS = { tpscod: '7' };
      mFHDR.hdtyps = hdtyps;

      const tBTYPSCollection: ITBTYPS[] = [{ tpscod: 'c' }];
      jest.spyOn(tBTYPSService, 'query').mockReturnValue(of(new HttpResponse({ body: tBTYPSCollection })));
      const additionalTBTYPS = [hdtyps];
      const expectedCollection: ITBTYPS[] = [...additionalTBTYPS, ...tBTYPSCollection];
      jest.spyOn(tBTYPSService, 'addTBTYPSToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ mFHDR });
      comp.ngOnInit();

      expect(tBTYPSService.query).toHaveBeenCalled();
      expect(tBTYPSService.addTBTYPSToCollectionIfMissing).toHaveBeenCalledWith(tBTYPSCollection, ...additionalTBTYPS);
      expect(comp.tBTYPSSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const mFHDR: IMFHDR = { hdno: 456 };
      const hdkota: ITBKAB = { kabcod: 'b342' };
      mFHDR.hdkota = hdkota;
      const hdprov: ITBPROV = { provcod: 'f6a3' };
      mFHDR.hdprov = hdprov;
      const hdneg: ITBNEG = { negcod: '259f' };
      mFHDR.hdneg = hdneg;
      const hdjnps: ITBJNPS = { jpscod: '4' };
      mFHDR.hdjnps = hdjnps;
      const hdtyps: ITBTYPS = { tpscod: 'd' };
      mFHDR.hdtyps = hdtyps;

      activatedRoute.data = of({ mFHDR });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(mFHDR));
      expect(comp.tBKABSSharedCollection).toContain(hdkota);
      expect(comp.tBPROVSSharedCollection).toContain(hdprov);
      expect(comp.tBNEGSSharedCollection).toContain(hdneg);
      expect(comp.tBJNPSSharedCollection).toContain(hdjnps);
      expect(comp.tBTYPSSharedCollection).toContain(hdtyps);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MFHDR>>();
      const mFHDR = { hdno: 123 };
      jest.spyOn(mFHDRService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mFHDR });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mFHDR }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(mFHDRService.update).toHaveBeenCalledWith(mFHDR);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MFHDR>>();
      const mFHDR = new MFHDR();
      jest.spyOn(mFHDRService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mFHDR });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: mFHDR }));
      saveSubject.complete();

      // THEN
      expect(mFHDRService.create).toHaveBeenCalledWith(mFHDR);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<MFHDR>>();
      const mFHDR = { hdno: 123 };
      jest.spyOn(mFHDRService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ mFHDR });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(mFHDRService.update).toHaveBeenCalledWith(mFHDR);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackTBKABByKabcod', () => {
      it('Should return tracked TBKAB primary key', () => {
        const entity = { kabcod: 'ABC' };
        const trackResult = comp.trackTBKABByKabcod(0, entity);
        expect(trackResult).toEqual(entity.kabcod);
      });
    });

    describe('trackTBPROVByProvcod', () => {
      it('Should return tracked TBPROV primary key', () => {
        const entity = { provcod: 'ABC' };
        const trackResult = comp.trackTBPROVByProvcod(0, entity);
        expect(trackResult).toEqual(entity.provcod);
      });
    });

    describe('trackTBNEGByNegcod', () => {
      it('Should return tracked TBNEG primary key', () => {
        const entity = { negcod: 'ABC' };
        const trackResult = comp.trackTBNEGByNegcod(0, entity);
        expect(trackResult).toEqual(entity.negcod);
      });
    });

    describe('trackTBJNPSByJpscod', () => {
      it('Should return tracked TBJNPS primary key', () => {
        const entity = { jpscod: 'ABC' };
        const trackResult = comp.trackTBJNPSByJpscod(0, entity);
        expect(trackResult).toEqual(entity.jpscod);
      });
    });

    describe('trackTBTYPSByTpscod', () => {
      it('Should return tracked TBTYPS primary key', () => {
        const entity = { tpscod: 'ABC' };
        const trackResult = comp.trackTBTYPSByTpscod(0, entity);
        expect(trackResult).toEqual(entity.tpscod);
      });
    });
  });
});
