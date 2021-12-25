import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITBTYPS, TBTYPS } from '../tbtyps.model';

import { TBTYPSService } from './tbtyps.service';

describe('TBTYPS Service', () => {
  let service: TBTYPSService;
  let httpMock: HttpTestingController;
  let elemDefault: ITBTYPS;
  let expectedResult: ITBTYPS | ITBTYPS[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TBTYPSService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      tpssts: 'AAAAAAA',
      tpscod: 'AAAAAAA',
      tpsnam: 'AAAAAAA',
      tpslmd: currentDate,
      tpsuid: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          tpslmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TBTYPS', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          tpslmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          tpslmd: currentDate,
        },
        returnedFromService
      );

      service.create(new TBTYPS()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TBTYPS', () => {
      const returnedFromService = Object.assign(
        {
          tpssts: 'BBBBBB',
          tpscod: 'BBBBBB',
          tpsnam: 'BBBBBB',
          tpslmd: currentDate.format(DATE_FORMAT),
          tpsuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          tpslmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TBTYPS', () => {
      const patchObject = Object.assign(
        {
          tpssts: 'BBBBBB',
          tpsnam: 'BBBBBB',
          tpslmd: currentDate.format(DATE_FORMAT),
        },
        new TBTYPS()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          tpslmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TBTYPS', () => {
      const returnedFromService = Object.assign(
        {
          tpssts: 'BBBBBB',
          tpscod: 'BBBBBB',
          tpsnam: 'BBBBBB',
          tpslmd: currentDate.format(DATE_FORMAT),
          tpsuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          tpslmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TBTYPS', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTBTYPSToCollectionIfMissing', () => {
      it('should add a TBTYPS to an empty array', () => {
        const tBTYPS: ITBTYPS = { tpscod: 'ABC' };
        expectedResult = service.addTBTYPSToCollectionIfMissing([], tBTYPS);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBTYPS);
      });

      it('should not add a TBTYPS to an array that contains it', () => {
        const tBTYPS: ITBTYPS = { tpscod: 'ABC' };
        const tBTYPSCollection: ITBTYPS[] = [
          {
            ...tBTYPS,
          },
          { tpscod: 'CBA' },
        ];
        expectedResult = service.addTBTYPSToCollectionIfMissing(tBTYPSCollection, tBTYPS);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TBTYPS to an array that doesn't contain it", () => {
        const tBTYPS: ITBTYPS = { tpscod: 'ABC' };
        const tBTYPSCollection: ITBTYPS[] = [{ tpscod: 'CBA' }];
        expectedResult = service.addTBTYPSToCollectionIfMissing(tBTYPSCollection, tBTYPS);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBTYPS);
      });

      it('should add only unique TBTYPS to an array', () => {
        const tBTYPSArray: ITBTYPS[] = [{ tpscod: 'ABC' }, { tpscod: 'CBA' }, { tpscod: '2' }];
        const tBTYPSCollection: ITBTYPS[] = [{ tpscod: 'ABC' }];
        expectedResult = service.addTBTYPSToCollectionIfMissing(tBTYPSCollection, ...tBTYPSArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tBTYPS: ITBTYPS = { tpscod: 'ABC' };
        const tBTYPS2: ITBTYPS = { tpscod: 'CBA' };
        expectedResult = service.addTBTYPSToCollectionIfMissing([], tBTYPS, tBTYPS2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBTYPS);
        expect(expectedResult).toContain(tBTYPS2);
      });

      it('should accept null and undefined values', () => {
        const tBTYPS: ITBTYPS = { tpscod: 'ABC' };
        expectedResult = service.addTBTYPSToCollectionIfMissing([], null, tBTYPS, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBTYPS);
      });

      it('should return initial array if no TBTYPS is added', () => {
        const tBTYPSCollection: ITBTYPS[] = [{ tpscod: 'ABC' }];
        expectedResult = service.addTBTYPSToCollectionIfMissing(tBTYPSCollection, undefined, null);
        expect(expectedResult).toEqual(tBTYPSCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
