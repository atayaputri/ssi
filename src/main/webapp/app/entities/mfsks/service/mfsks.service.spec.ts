import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IMFSKS, MFSKS } from '../mfsks.model';

import { MFSKSService } from './mfsks.service';

describe('MFSKS Service', () => {
  let service: MFSKSService;
  let httpMock: HttpTestingController;
  let elemDefault: IMFSKS;
  let expectedResult: IMFSKS | IMFSKS[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MFSKSService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      sksts: 'AAAAAAA',
      skno: 0,
      skjsh: 0,
      skbat: 0,
      skseq: 0,
      skref: 'AAAAAAA',
      skdis: currentDate,
      sklmd: currentDate,
      skuid: 'AAAAAAA',
      skfil1: 0,
      skfil2: 0,
      skfil3: 'AAAAAAA',
      skfil4: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          skdis: currentDate.format(DATE_FORMAT),
          sklmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a MFSKS', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          skdis: currentDate.format(DATE_FORMAT),
          sklmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          skdis: currentDate,
          sklmd: currentDate,
        },
        returnedFromService
      );

      service.create(new MFSKS()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MFSKS', () => {
      const returnedFromService = Object.assign(
        {
          sksts: 'BBBBBB',
          skno: 1,
          skjsh: 1,
          skbat: 1,
          skseq: 1,
          skref: 'BBBBBB',
          skdis: currentDate.format(DATE_FORMAT),
          sklmd: currentDate.format(DATE_FORMAT),
          skuid: 'BBBBBB',
          skfil1: 1,
          skfil2: 1,
          skfil3: 'BBBBBB',
          skfil4: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          skdis: currentDate,
          sklmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MFSKS', () => {
      const patchObject = Object.assign(
        {
          sksts: 'BBBBBB',
          skjsh: 1,
          skref: 'BBBBBB',
          skdis: currentDate.format(DATE_FORMAT),
          skfil1: 1,
          skfil2: 1,
          skfil4: 'BBBBBB',
        },
        new MFSKS()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          skdis: currentDate,
          sklmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MFSKS', () => {
      const returnedFromService = Object.assign(
        {
          sksts: 'BBBBBB',
          skno: 1,
          skjsh: 1,
          skbat: 1,
          skseq: 1,
          skref: 'BBBBBB',
          skdis: currentDate.format(DATE_FORMAT),
          sklmd: currentDate.format(DATE_FORMAT),
          skuid: 'BBBBBB',
          skfil1: 1,
          skfil2: 1,
          skfil3: 'BBBBBB',
          skfil4: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          skdis: currentDate,
          sklmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a MFSKS', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMFSKSToCollectionIfMissing', () => {
      it('should add a MFSKS to an empty array', () => {
        const mFSKS: IMFSKS = { skno: 123 };
        expectedResult = service.addMFSKSToCollectionIfMissing([], mFSKS);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mFSKS);
      });

      it('should not add a MFSKS to an array that contains it', () => {
        const mFSKS: IMFSKS = { skno: 123 };
        const mFSKSCollection: IMFSKS[] = [
          {
            ...mFSKS,
          },
          { skno: 456 },
        ];
        expectedResult = service.addMFSKSToCollectionIfMissing(mFSKSCollection, mFSKS);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MFSKS to an array that doesn't contain it", () => {
        const mFSKS: IMFSKS = { skno: 123 };
        const mFSKSCollection: IMFSKS[] = [{ skno: 456 }];
        expectedResult = service.addMFSKSToCollectionIfMissing(mFSKSCollection, mFSKS);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mFSKS);
      });

      it('should add only unique MFSKS to an array', () => {
        const mFSKSArray: IMFSKS[] = [{ skno: 123 }, { skno: 456 }, { skno: 94210 }];
        const mFSKSCollection: IMFSKS[] = [{ skno: 123 }];
        expectedResult = service.addMFSKSToCollectionIfMissing(mFSKSCollection, ...mFSKSArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mFSKS: IMFSKS = { skno: 123 };
        const mFSKS2: IMFSKS = { skno: 456 };
        expectedResult = service.addMFSKSToCollectionIfMissing([], mFSKS, mFSKS2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mFSKS);
        expect(expectedResult).toContain(mFSKS2);
      });

      it('should accept null and undefined values', () => {
        const mFSKS: IMFSKS = { skno: 123 };
        expectedResult = service.addMFSKSToCollectionIfMissing([], null, mFSKS, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mFSKS);
      });

      it('should return initial array if no MFSKS is added', () => {
        const mFSKSCollection: IMFSKS[] = [{ skno: 123 }];
        expectedResult = service.addMFSKSToCollectionIfMissing(mFSKSCollection, undefined, null);
        expect(expectedResult).toEqual(mFSKSCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
