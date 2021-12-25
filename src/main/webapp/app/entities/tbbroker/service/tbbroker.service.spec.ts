import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITBBROKER, TBBROKER } from '../tbbroker.model';

import { TBBROKERService } from './tbbroker.service';

describe('TBBROKER Service', () => {
  let service: TBBROKERService;
  let httpMock: HttpTestingController;
  let elemDefault: ITBBROKER;
  let expectedResult: ITBBROKER | ITBBROKER[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TBBROKERService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      brsts: 'AAAAAAA',
      brcode: 'AAAAAAA',
      brnam: 'AAAAAAA',
      brlmd: currentDate,
      bruid: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          brlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TBBROKER', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          brlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          brlmd: currentDate,
        },
        returnedFromService
      );

      service.create(new TBBROKER()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TBBROKER', () => {
      const returnedFromService = Object.assign(
        {
          brsts: 'BBBBBB',
          brcode: 'BBBBBB',
          brnam: 'BBBBBB',
          brlmd: currentDate.format(DATE_FORMAT),
          bruid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          brlmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TBBROKER', () => {
      const patchObject = Object.assign(
        {
          brsts: 'BBBBBB',
          bruid: 'BBBBBB',
        },
        new TBBROKER()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          brlmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TBBROKER', () => {
      const returnedFromService = Object.assign(
        {
          brsts: 'BBBBBB',
          brcode: 'BBBBBB',
          brnam: 'BBBBBB',
          brlmd: currentDate.format(DATE_FORMAT),
          bruid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          brlmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TBBROKER', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTBBROKERToCollectionIfMissing', () => {
      it('should add a TBBROKER to an empty array', () => {
        const tBBROKER: ITBBROKER = { brcode: 'ABC' };
        expectedResult = service.addTBBROKERToCollectionIfMissing([], tBBROKER);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBBROKER);
      });

      it('should not add a TBBROKER to an array that contains it', () => {
        const tBBROKER: ITBBROKER = { brcode: 'ABC' };
        const tBBROKERCollection: ITBBROKER[] = [
          {
            ...tBBROKER,
          },
          { brcode: 'CBA' },
        ];
        expectedResult = service.addTBBROKERToCollectionIfMissing(tBBROKERCollection, tBBROKER);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TBBROKER to an array that doesn't contain it", () => {
        const tBBROKER: ITBBROKER = { brcode: 'ABC' };
        const tBBROKERCollection: ITBBROKER[] = [{ brcode: 'CBA' }];
        expectedResult = service.addTBBROKERToCollectionIfMissing(tBBROKERCollection, tBBROKER);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBBROKER);
      });

      it('should add only unique TBBROKER to an array', () => {
        const tBBROKERArray: ITBBROKER[] = [{ brcode: 'ABC' }, { brcode: 'CBA' }, { brcode: '63' }];
        const tBBROKERCollection: ITBBROKER[] = [{ brcode: 'ABC' }];
        expectedResult = service.addTBBROKERToCollectionIfMissing(tBBROKERCollection, ...tBBROKERArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tBBROKER: ITBBROKER = { brcode: 'ABC' };
        const tBBROKER2: ITBBROKER = { brcode: 'CBA' };
        expectedResult = service.addTBBROKERToCollectionIfMissing([], tBBROKER, tBBROKER2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBBROKER);
        expect(expectedResult).toContain(tBBROKER2);
      });

      it('should accept null and undefined values', () => {
        const tBBROKER: ITBBROKER = { brcode: 'ABC' };
        expectedResult = service.addTBBROKERToCollectionIfMissing([], null, tBBROKER, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBBROKER);
      });

      it('should return initial array if no TBBROKER is added', () => {
        const tBBROKERCollection: ITBBROKER[] = [{ brcode: 'ABC' }];
        expectedResult = service.addTBBROKERToCollectionIfMissing(tBBROKERCollection, undefined, null);
        expect(expectedResult).toEqual(tBBROKERCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
