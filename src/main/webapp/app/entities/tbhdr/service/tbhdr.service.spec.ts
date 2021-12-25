import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITBHDR, TBHDR } from '../tbhdr.model';

import { TBHDRService } from './tbhdr.service';

describe('TBHDR Service', () => {
  let service: TBHDRService;
  let httpMock: HttpTestingController;
  let elemDefault: ITBHDR;
  let expectedResult: ITBHDR | ITBHDR[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TBHDRService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      thsts: 'AAAAAAA',
      thno: 0,
      thsid: 'AAAAAAA',
      thnm1: 'AAAAAAA',
      thjsh: 0,
      thtax: 0,
      thdis: currentDate,
      thlmd: currentDate,
      thuid: 'AAAAAAA',
      thfil1: 0,
      thfil2: 0,
      thfil3: 'AAAAAAA',
      thfil4: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          thdis: currentDate.format(DATE_FORMAT),
          thlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TBHDR', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          thdis: currentDate.format(DATE_FORMAT),
          thlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          thdis: currentDate,
          thlmd: currentDate,
        },
        returnedFromService
      );

      service.create(new TBHDR()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TBHDR', () => {
      const returnedFromService = Object.assign(
        {
          thsts: 'BBBBBB',
          thno: 1,
          thsid: 'BBBBBB',
          thnm1: 'BBBBBB',
          thjsh: 1,
          thtax: 1,
          thdis: currentDate.format(DATE_FORMAT),
          thlmd: currentDate.format(DATE_FORMAT),
          thuid: 'BBBBBB',
          thfil1: 1,
          thfil2: 1,
          thfil3: 'BBBBBB',
          thfil4: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          thdis: currentDate,
          thlmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TBHDR', () => {
      const patchObject = Object.assign(
        {
          thsts: 'BBBBBB',
          thsid: 'BBBBBB',
          thdis: currentDate.format(DATE_FORMAT),
          thuid: 'BBBBBB',
          thfil4: 'BBBBBB',
        },
        new TBHDR()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          thdis: currentDate,
          thlmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TBHDR', () => {
      const returnedFromService = Object.assign(
        {
          thsts: 'BBBBBB',
          thno: 1,
          thsid: 'BBBBBB',
          thnm1: 'BBBBBB',
          thjsh: 1,
          thtax: 1,
          thdis: currentDate.format(DATE_FORMAT),
          thlmd: currentDate.format(DATE_FORMAT),
          thuid: 'BBBBBB',
          thfil1: 1,
          thfil2: 1,
          thfil3: 'BBBBBB',
          thfil4: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          thdis: currentDate,
          thlmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TBHDR', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTBHDRToCollectionIfMissing', () => {
      it('should add a TBHDR to an empty array', () => {
        const tBHDR: ITBHDR = { thno: 123 };
        expectedResult = service.addTBHDRToCollectionIfMissing([], tBHDR);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBHDR);
      });

      it('should not add a TBHDR to an array that contains it', () => {
        const tBHDR: ITBHDR = { thno: 123 };
        const tBHDRCollection: ITBHDR[] = [
          {
            ...tBHDR,
          },
          { thno: 456 },
        ];
        expectedResult = service.addTBHDRToCollectionIfMissing(tBHDRCollection, tBHDR);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TBHDR to an array that doesn't contain it", () => {
        const tBHDR: ITBHDR = { thno: 123 };
        const tBHDRCollection: ITBHDR[] = [{ thno: 456 }];
        expectedResult = service.addTBHDRToCollectionIfMissing(tBHDRCollection, tBHDR);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBHDR);
      });

      it('should add only unique TBHDR to an array', () => {
        const tBHDRArray: ITBHDR[] = [{ thno: 123 }, { thno: 456 }, { thno: 52031 }];
        const tBHDRCollection: ITBHDR[] = [{ thno: 123 }];
        expectedResult = service.addTBHDRToCollectionIfMissing(tBHDRCollection, ...tBHDRArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tBHDR: ITBHDR = { thno: 123 };
        const tBHDR2: ITBHDR = { thno: 456 };
        expectedResult = service.addTBHDRToCollectionIfMissing([], tBHDR, tBHDR2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBHDR);
        expect(expectedResult).toContain(tBHDR2);
      });

      it('should accept null and undefined values', () => {
        const tBHDR: ITBHDR = { thno: 123 };
        expectedResult = service.addTBHDRToCollectionIfMissing([], null, tBHDR, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBHDR);
      });

      it('should return initial array if no TBHDR is added', () => {
        const tBHDRCollection: ITBHDR[] = [{ thno: 123 }];
        expectedResult = service.addTBHDRToCollectionIfMissing(tBHDRCollection, undefined, null);
        expect(expectedResult).toEqual(tBHDRCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
