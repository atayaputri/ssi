import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITBCOMS, TBCOMS } from '../tbcoms.model';

import { TBCOMSService } from './tbcoms.service';

describe('TBCOMS Service', () => {
  let service: TBCOMSService;
  let httpMock: HttpTestingController;
  let elemDefault: ITBCOMS;
  let expectedResult: ITBCOMS | ITBCOMS[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TBCOMSService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      costs: 'AAAAAAA',
      cocode: 'AAAAAAA',
      conam: 'AAAAAAA',
      cocbei: 'AAAAAAA',
      conbei: 'AAAAAAA',
      cosat: 'AAAAAAA',
      conom: 0,
      coisin: 'AAAAAAA',
      conpwp: 'AAAAAAA',
      coseri: 'AAAAAAA',
      colshm: 0,
      colsks: 0,
      cotshm: 0,
      codshm: 0,
      conote1: 'AAAAAAA',
      conote2: 'AAAAAAA',
      conote3: 'AAAAAAA',
      coskps: 0,
      cothld: 0,
      codir1: 'AAAAAAA',
      codir2: 'AAAAAAA',
      codir3: 'AAAAAAA',
      codir4: 'AAAAAAA',
      codir5: 'AAAAAAA',
      colmd: currentDate,
      couid: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          colmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TBCOMS', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          colmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          colmd: currentDate,
        },
        returnedFromService
      );

      service.create(new TBCOMS()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TBCOMS', () => {
      const returnedFromService = Object.assign(
        {
          costs: 'BBBBBB',
          cocode: 'BBBBBB',
          conam: 'BBBBBB',
          cocbei: 'BBBBBB',
          conbei: 'BBBBBB',
          cosat: 'BBBBBB',
          conom: 1,
          coisin: 'BBBBBB',
          conpwp: 'BBBBBB',
          coseri: 'BBBBBB',
          colshm: 1,
          colsks: 1,
          cotshm: 1,
          codshm: 1,
          conote1: 'BBBBBB',
          conote2: 'BBBBBB',
          conote3: 'BBBBBB',
          coskps: 1,
          cothld: 1,
          codir1: 'BBBBBB',
          codir2: 'BBBBBB',
          codir3: 'BBBBBB',
          codir4: 'BBBBBB',
          codir5: 'BBBBBB',
          colmd: currentDate.format(DATE_FORMAT),
          couid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          colmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TBCOMS', () => {
      const patchObject = Object.assign(
        {
          costs: 'BBBBBB',
          cocbei: 'BBBBBB',
          conbei: 'BBBBBB',
          cosat: 'BBBBBB',
          conom: 1,
          coisin: 'BBBBBB',
          conpwp: 'BBBBBB',
          coseri: 'BBBBBB',
          colsks: 1,
          cotshm: 1,
          codshm: 1,
          conote3: 'BBBBBB',
          codir3: 'BBBBBB',
          codir5: 'BBBBBB',
        },
        new TBCOMS()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          colmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TBCOMS', () => {
      const returnedFromService = Object.assign(
        {
          costs: 'BBBBBB',
          cocode: 'BBBBBB',
          conam: 'BBBBBB',
          cocbei: 'BBBBBB',
          conbei: 'BBBBBB',
          cosat: 'BBBBBB',
          conom: 1,
          coisin: 'BBBBBB',
          conpwp: 'BBBBBB',
          coseri: 'BBBBBB',
          colshm: 1,
          colsks: 1,
          cotshm: 1,
          codshm: 1,
          conote1: 'BBBBBB',
          conote2: 'BBBBBB',
          conote3: 'BBBBBB',
          coskps: 1,
          cothld: 1,
          codir1: 'BBBBBB',
          codir2: 'BBBBBB',
          codir3: 'BBBBBB',
          codir4: 'BBBBBB',
          codir5: 'BBBBBB',
          colmd: currentDate.format(DATE_FORMAT),
          couid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          colmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TBCOMS', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTBCOMSToCollectionIfMissing', () => {
      it('should add a TBCOMS to an empty array', () => {
        const tBCOMS: ITBCOMS = { cocode: 'ABC' };
        expectedResult = service.addTBCOMSToCollectionIfMissing([], tBCOMS);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBCOMS);
      });

      it('should not add a TBCOMS to an array that contains it', () => {
        const tBCOMS: ITBCOMS = { cocode: 'ABC' };
        const tBCOMSCollection: ITBCOMS[] = [
          {
            ...tBCOMS,
          },
          { cocode: 'CBA' },
        ];
        expectedResult = service.addTBCOMSToCollectionIfMissing(tBCOMSCollection, tBCOMS);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TBCOMS to an array that doesn't contain it", () => {
        const tBCOMS: ITBCOMS = { cocode: 'ABC' };
        const tBCOMSCollection: ITBCOMS[] = [{ cocode: 'CBA' }];
        expectedResult = service.addTBCOMSToCollectionIfMissing(tBCOMSCollection, tBCOMS);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBCOMS);
      });

      it('should add only unique TBCOMS to an array', () => {
        const tBCOMSArray: ITBCOMS[] = [{ cocode: 'ABC' }, { cocode: 'CBA' }, { cocode: 'dc727572-8' }];
        const tBCOMSCollection: ITBCOMS[] = [{ cocode: 'ABC' }];
        expectedResult = service.addTBCOMSToCollectionIfMissing(tBCOMSCollection, ...tBCOMSArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tBCOMS: ITBCOMS = { cocode: 'ABC' };
        const tBCOMS2: ITBCOMS = { cocode: 'CBA' };
        expectedResult = service.addTBCOMSToCollectionIfMissing([], tBCOMS, tBCOMS2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBCOMS);
        expect(expectedResult).toContain(tBCOMS2);
      });

      it('should accept null and undefined values', () => {
        const tBCOMS: ITBCOMS = { cocode: 'ABC' };
        expectedResult = service.addTBCOMSToCollectionIfMissing([], null, tBCOMS, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBCOMS);
      });

      it('should return initial array if no TBCOMS is added', () => {
        const tBCOMSCollection: ITBCOMS[] = [{ cocode: 'ABC' }];
        expectedResult = service.addTBCOMSToCollectionIfMissing(tBCOMSCollection, undefined, null);
        expect(expectedResult).toEqual(tBCOMSCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
