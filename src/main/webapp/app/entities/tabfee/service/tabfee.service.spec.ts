import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITABFEE, TABFEE } from '../tabfee.model';

import { TABFEEService } from './tabfee.service';

describe('TABFEE Service', () => {
  let service: TABFEEService;
  let httpMock: HttpTestingController;
  let elemDefault: ITABFEE;
  let expectedResult: ITABFEE | ITABFEE[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TABFEEService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      fests: 'AAAAAAA',
      feemt: 'AAAAAAA',
      femin: 0,
      femax: 0,
      fefee: 0,
      fediscp: 0,
      fedisc: 0,
      fetax: 0,
      felmd: currentDate,
      feuid: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          felmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TABFEE', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          felmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          felmd: currentDate,
        },
        returnedFromService
      );

      service.create(new TABFEE()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TABFEE', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fests: 'BBBBBB',
          feemt: 'BBBBBB',
          femin: 1,
          femax: 1,
          fefee: 1,
          fediscp: 1,
          fedisc: 1,
          fetax: 1,
          felmd: currentDate.format(DATE_FORMAT),
          feuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          felmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TABFEE', () => {
      const patchObject = Object.assign(
        {
          femax: 1,
          fefee: 1,
          feuid: 'BBBBBB',
        },
        new TABFEE()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          felmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TABFEE', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          fests: 'BBBBBB',
          feemt: 'BBBBBB',
          femin: 1,
          femax: 1,
          fefee: 1,
          fediscp: 1,
          fedisc: 1,
          fetax: 1,
          felmd: currentDate.format(DATE_FORMAT),
          feuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          felmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TABFEE', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTABFEEToCollectionIfMissing', () => {
      it('should add a TABFEE to an empty array', () => {
        const tABFEE: ITABFEE = { id: 123 };
        expectedResult = service.addTABFEEToCollectionIfMissing([], tABFEE);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tABFEE);
      });

      it('should not add a TABFEE to an array that contains it', () => {
        const tABFEE: ITABFEE = { id: 123 };
        const tABFEECollection: ITABFEE[] = [
          {
            ...tABFEE,
          },
          { id: 456 },
        ];
        expectedResult = service.addTABFEEToCollectionIfMissing(tABFEECollection, tABFEE);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TABFEE to an array that doesn't contain it", () => {
        const tABFEE: ITABFEE = { id: 123 };
        const tABFEECollection: ITABFEE[] = [{ id: 456 }];
        expectedResult = service.addTABFEEToCollectionIfMissing(tABFEECollection, tABFEE);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tABFEE);
      });

      it('should add only unique TABFEE to an array', () => {
        const tABFEEArray: ITABFEE[] = [{ id: 123 }, { id: 456 }, { id: 24976 }];
        const tABFEECollection: ITABFEE[] = [{ id: 123 }];
        expectedResult = service.addTABFEEToCollectionIfMissing(tABFEECollection, ...tABFEEArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tABFEE: ITABFEE = { id: 123 };
        const tABFEE2: ITABFEE = { id: 456 };
        expectedResult = service.addTABFEEToCollectionIfMissing([], tABFEE, tABFEE2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tABFEE);
        expect(expectedResult).toContain(tABFEE2);
      });

      it('should accept null and undefined values', () => {
        const tABFEE: ITABFEE = { id: 123 };
        expectedResult = service.addTABFEEToCollectionIfMissing([], null, tABFEE, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tABFEE);
      });

      it('should return initial array if no TABFEE is added', () => {
        const tABFEECollection: ITABFEE[] = [{ id: 123 }];
        expectedResult = service.addTABFEEToCollectionIfMissing(tABFEECollection, undefined, null);
        expect(expectedResult).toEqual(tABFEECollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
