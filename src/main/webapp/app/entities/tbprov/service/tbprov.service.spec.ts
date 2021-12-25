import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITBPROV, TBPROV } from '../tbprov.model';

import { TBPROVService } from './tbprov.service';

describe('TBPROV Service', () => {
  let service: TBPROVService;
  let httpMock: HttpTestingController;
  let elemDefault: ITBPROV;
  let expectedResult: ITBPROV | ITBPROV[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TBPROVService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      provsts: 'AAAAAAA',
      provcod: 'AAAAAAA',
      provnam: 'AAAAAAA',
      provlmd: currentDate,
      provuid: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          provlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TBPROV', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          provlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          provlmd: currentDate,
        },
        returnedFromService
      );

      service.create(new TBPROV()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TBPROV', () => {
      const returnedFromService = Object.assign(
        {
          provsts: 'BBBBBB',
          provcod: 'BBBBBB',
          provnam: 'BBBBBB',
          provlmd: currentDate.format(DATE_FORMAT),
          provuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          provlmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TBPROV', () => {
      const patchObject = Object.assign(
        {
          provsts: 'BBBBBB',
          provnam: 'BBBBBB',
        },
        new TBPROV()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          provlmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TBPROV', () => {
      const returnedFromService = Object.assign(
        {
          provsts: 'BBBBBB',
          provcod: 'BBBBBB',
          provnam: 'BBBBBB',
          provlmd: currentDate.format(DATE_FORMAT),
          provuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          provlmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TBPROV', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTBPROVToCollectionIfMissing', () => {
      it('should add a TBPROV to an empty array', () => {
        const tBPROV: ITBPROV = { provcod: 'ABC' };
        expectedResult = service.addTBPROVToCollectionIfMissing([], tBPROV);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBPROV);
      });

      it('should not add a TBPROV to an array that contains it', () => {
        const tBPROV: ITBPROV = { provcod: 'ABC' };
        const tBPROVCollection: ITBPROV[] = [
          {
            ...tBPROV,
          },
          { provcod: 'CBA' },
        ];
        expectedResult = service.addTBPROVToCollectionIfMissing(tBPROVCollection, tBPROV);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TBPROV to an array that doesn't contain it", () => {
        const tBPROV: ITBPROV = { provcod: 'ABC' };
        const tBPROVCollection: ITBPROV[] = [{ provcod: 'CBA' }];
        expectedResult = service.addTBPROVToCollectionIfMissing(tBPROVCollection, tBPROV);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBPROV);
      });

      it('should add only unique TBPROV to an array', () => {
        const tBPROVArray: ITBPROV[] = [{ provcod: 'ABC' }, { provcod: 'CBA' }, { provcod: 'fbfd' }];
        const tBPROVCollection: ITBPROV[] = [{ provcod: 'ABC' }];
        expectedResult = service.addTBPROVToCollectionIfMissing(tBPROVCollection, ...tBPROVArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tBPROV: ITBPROV = { provcod: 'ABC' };
        const tBPROV2: ITBPROV = { provcod: 'CBA' };
        expectedResult = service.addTBPROVToCollectionIfMissing([], tBPROV, tBPROV2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBPROV);
        expect(expectedResult).toContain(tBPROV2);
      });

      it('should accept null and undefined values', () => {
        const tBPROV: ITBPROV = { provcod: 'ABC' };
        expectedResult = service.addTBPROVToCollectionIfMissing([], null, tBPROV, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBPROV);
      });

      it('should return initial array if no TBPROV is added', () => {
        const tBPROVCollection: ITBPROV[] = [{ provcod: 'ABC' }];
        expectedResult = service.addTBPROVToCollectionIfMissing(tBPROVCollection, undefined, null);
        expect(expectedResult).toEqual(tBPROVCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
