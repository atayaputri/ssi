import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITBKAB, TBKAB } from '../tbkab.model';

import { TBKABService } from './tbkab.service';

describe('TBKAB Service', () => {
  let service: TBKABService;
  let httpMock: HttpTestingController;
  let elemDefault: ITBKAB;
  let expectedResult: ITBKAB | ITBKAB[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TBKABService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      kabsts: 'AAAAAAA',
      kabcod: 'AAAAAAA',
      kabnam: 'AAAAAAA',
      kablmd: currentDate,
      kabuid: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          kablmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TBKAB', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          kablmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          kablmd: currentDate,
        },
        returnedFromService
      );

      service.create(new TBKAB()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TBKAB', () => {
      const returnedFromService = Object.assign(
        {
          kabsts: 'BBBBBB',
          kabcod: 'BBBBBB',
          kabnam: 'BBBBBB',
          kablmd: currentDate.format(DATE_FORMAT),
          kabuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          kablmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TBKAB', () => {
      const patchObject = Object.assign(
        {
          kabsts: 'BBBBBB',
          kabnam: 'BBBBBB',
          kablmd: currentDate.format(DATE_FORMAT),
        },
        new TBKAB()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          kablmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TBKAB', () => {
      const returnedFromService = Object.assign(
        {
          kabsts: 'BBBBBB',
          kabcod: 'BBBBBB',
          kabnam: 'BBBBBB',
          kablmd: currentDate.format(DATE_FORMAT),
          kabuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          kablmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TBKAB', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTBKABToCollectionIfMissing', () => {
      it('should add a TBKAB to an empty array', () => {
        const tBKAB: ITBKAB = { kabcod: 'ABC' };
        expectedResult = service.addTBKABToCollectionIfMissing([], tBKAB);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBKAB);
      });

      it('should not add a TBKAB to an array that contains it', () => {
        const tBKAB: ITBKAB = { kabcod: 'ABC' };
        const tBKABCollection: ITBKAB[] = [
          {
            ...tBKAB,
          },
          { kabcod: 'CBA' },
        ];
        expectedResult = service.addTBKABToCollectionIfMissing(tBKABCollection, tBKAB);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TBKAB to an array that doesn't contain it", () => {
        const tBKAB: ITBKAB = { kabcod: 'ABC' };
        const tBKABCollection: ITBKAB[] = [{ kabcod: 'CBA' }];
        expectedResult = service.addTBKABToCollectionIfMissing(tBKABCollection, tBKAB);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBKAB);
      });

      it('should add only unique TBKAB to an array', () => {
        const tBKABArray: ITBKAB[] = [{ kabcod: 'ABC' }, { kabcod: 'CBA' }, { kabcod: '382e' }];
        const tBKABCollection: ITBKAB[] = [{ kabcod: 'ABC' }];
        expectedResult = service.addTBKABToCollectionIfMissing(tBKABCollection, ...tBKABArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tBKAB: ITBKAB = { kabcod: 'ABC' };
        const tBKAB2: ITBKAB = { kabcod: 'CBA' };
        expectedResult = service.addTBKABToCollectionIfMissing([], tBKAB, tBKAB2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBKAB);
        expect(expectedResult).toContain(tBKAB2);
      });

      it('should accept null and undefined values', () => {
        const tBKAB: ITBKAB = { kabcod: 'ABC' };
        expectedResult = service.addTBKABToCollectionIfMissing([], null, tBKAB, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBKAB);
      });

      it('should return initial array if no TBKAB is added', () => {
        const tBKABCollection: ITBKAB[] = [{ kabcod: 'ABC' }];
        expectedResult = service.addTBKABToCollectionIfMissing(tBKABCollection, undefined, null);
        expect(expectedResult).toEqual(tBKABCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
