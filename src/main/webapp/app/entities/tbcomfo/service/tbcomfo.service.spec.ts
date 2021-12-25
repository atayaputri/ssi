import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITBCOMFO, TBCOMFO } from '../tbcomfo.model';

import { TBCOMFOService } from './tbcomfo.service';

describe('TBCOMFO Service', () => {
  let service: TBCOMFOService;
  let httpMock: HttpTestingController;
  let elemDefault: ITBCOMFO;
  let expectedResult: ITBCOMFO | ITBCOMFO[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TBCOMFOService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      costs: 'AAAAAAA',
      cocode: 'AAAAAAA',
      conam: 'AAAAAAA',
      cocbei: 'AAAAAAA',
      conbei: 'AAAAAAA',
      cosat: 'AAAAAAA',
      conom: 0,
      coseri: 'AAAAAAA',
      codir: 'AAAAAAA',
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

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TBCOMFO', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
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

      service.create(new TBCOMFO()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TBCOMFO', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          costs: 'BBBBBB',
          cocode: 'BBBBBB',
          conam: 'BBBBBB',
          cocbei: 'BBBBBB',
          conbei: 'BBBBBB',
          cosat: 'BBBBBB',
          conom: 1,
          coseri: 'BBBBBB',
          codir: 'BBBBBB',
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

    it('should partial update a TBCOMFO', () => {
      const patchObject = Object.assign(
        {
          conam: 'BBBBBB',
          codir: 'BBBBBB',
          colmd: currentDate.format(DATE_FORMAT),
          couid: 'BBBBBB',
        },
        new TBCOMFO()
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

    it('should return a list of TBCOMFO', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          costs: 'BBBBBB',
          cocode: 'BBBBBB',
          conam: 'BBBBBB',
          cocbei: 'BBBBBB',
          conbei: 'BBBBBB',
          cosat: 'BBBBBB',
          conom: 1,
          coseri: 'BBBBBB',
          codir: 'BBBBBB',
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

    it('should delete a TBCOMFO', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTBCOMFOToCollectionIfMissing', () => {
      it('should add a TBCOMFO to an empty array', () => {
        const tBCOMFO: ITBCOMFO = { id: 123 };
        expectedResult = service.addTBCOMFOToCollectionIfMissing([], tBCOMFO);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBCOMFO);
      });

      it('should not add a TBCOMFO to an array that contains it', () => {
        const tBCOMFO: ITBCOMFO = { id: 123 };
        const tBCOMFOCollection: ITBCOMFO[] = [
          {
            ...tBCOMFO,
          },
          { id: 456 },
        ];
        expectedResult = service.addTBCOMFOToCollectionIfMissing(tBCOMFOCollection, tBCOMFO);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TBCOMFO to an array that doesn't contain it", () => {
        const tBCOMFO: ITBCOMFO = { id: 123 };
        const tBCOMFOCollection: ITBCOMFO[] = [{ id: 456 }];
        expectedResult = service.addTBCOMFOToCollectionIfMissing(tBCOMFOCollection, tBCOMFO);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBCOMFO);
      });

      it('should add only unique TBCOMFO to an array', () => {
        const tBCOMFOArray: ITBCOMFO[] = [{ id: 123 }, { id: 456 }, { id: 86712 }];
        const tBCOMFOCollection: ITBCOMFO[] = [{ id: 123 }];
        expectedResult = service.addTBCOMFOToCollectionIfMissing(tBCOMFOCollection, ...tBCOMFOArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tBCOMFO: ITBCOMFO = { id: 123 };
        const tBCOMFO2: ITBCOMFO = { id: 456 };
        expectedResult = service.addTBCOMFOToCollectionIfMissing([], tBCOMFO, tBCOMFO2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBCOMFO);
        expect(expectedResult).toContain(tBCOMFO2);
      });

      it('should accept null and undefined values', () => {
        const tBCOMFO: ITBCOMFO = { id: 123 };
        expectedResult = service.addTBCOMFOToCollectionIfMissing([], null, tBCOMFO, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBCOMFO);
      });

      it('should return initial array if no TBCOMFO is added', () => {
        const tBCOMFOCollection: ITBCOMFO[] = [{ id: 123 }];
        expectedResult = service.addTBCOMFOToCollectionIfMissing(tBCOMFOCollection, undefined, null);
        expect(expectedResult).toEqual(tBCOMFOCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
