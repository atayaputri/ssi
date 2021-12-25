import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITBJNSHM, TBJNSHM } from '../tbjnshm.model';

import { TBJNSHMService } from './tbjnshm.service';

describe('TBJNSHM Service', () => {
  let service: TBJNSHMService;
  let httpMock: HttpTestingController;
  let elemDefault: ITBJNSHM;
  let expectedResult: ITBJNSHM | ITBJNSHM[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TBJNSHMService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      jshsts: 'AAAAAAA',
      jshcod: 'AAAAAAA',
      jshnam: 'AAAAAAA',
      jshlmd: currentDate,
      jshuid: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          jshlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TBJNSHM', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          jshlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          jshlmd: currentDate,
        },
        returnedFromService
      );

      service.create(new TBJNSHM()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TBJNSHM', () => {
      const returnedFromService = Object.assign(
        {
          jshsts: 'BBBBBB',
          jshcod: 'BBBBBB',
          jshnam: 'BBBBBB',
          jshlmd: currentDate.format(DATE_FORMAT),
          jshuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          jshlmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TBJNSHM', () => {
      const patchObject = Object.assign(
        {
          jshsts: 'BBBBBB',
          jshnam: 'BBBBBB',
          jshlmd: currentDate.format(DATE_FORMAT),
        },
        new TBJNSHM()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          jshlmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TBJNSHM', () => {
      const returnedFromService = Object.assign(
        {
          jshsts: 'BBBBBB',
          jshcod: 'BBBBBB',
          jshnam: 'BBBBBB',
          jshlmd: currentDate.format(DATE_FORMAT),
          jshuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          jshlmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TBJNSHM', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTBJNSHMToCollectionIfMissing', () => {
      it('should add a TBJNSHM to an empty array', () => {
        const tBJNSHM: ITBJNSHM = { jshcod: 'ABC' };
        expectedResult = service.addTBJNSHMToCollectionIfMissing([], tBJNSHM);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBJNSHM);
      });

      it('should not add a TBJNSHM to an array that contains it', () => {
        const tBJNSHM: ITBJNSHM = { jshcod: 'ABC' };
        const tBJNSHMCollection: ITBJNSHM[] = [
          {
            ...tBJNSHM,
          },
          { jshcod: 'CBA' },
        ];
        expectedResult = service.addTBJNSHMToCollectionIfMissing(tBJNSHMCollection, tBJNSHM);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TBJNSHM to an array that doesn't contain it", () => {
        const tBJNSHM: ITBJNSHM = { jshcod: 'ABC' };
        const tBJNSHMCollection: ITBJNSHM[] = [{ jshcod: 'CBA' }];
        expectedResult = service.addTBJNSHMToCollectionIfMissing(tBJNSHMCollection, tBJNSHM);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBJNSHM);
      });

      it('should add only unique TBJNSHM to an array', () => {
        const tBJNSHMArray: ITBJNSHM[] = [{ jshcod: 'ABC' }, { jshcod: 'CBA' }, { jshcod: '5' }];
        const tBJNSHMCollection: ITBJNSHM[] = [{ jshcod: 'ABC' }];
        expectedResult = service.addTBJNSHMToCollectionIfMissing(tBJNSHMCollection, ...tBJNSHMArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tBJNSHM: ITBJNSHM = { jshcod: 'ABC' };
        const tBJNSHM2: ITBJNSHM = { jshcod: 'CBA' };
        expectedResult = service.addTBJNSHMToCollectionIfMissing([], tBJNSHM, tBJNSHM2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBJNSHM);
        expect(expectedResult).toContain(tBJNSHM2);
      });

      it('should accept null and undefined values', () => {
        const tBJNSHM: ITBJNSHM = { jshcod: 'ABC' };
        expectedResult = service.addTBJNSHMToCollectionIfMissing([], null, tBJNSHM, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBJNSHM);
      });

      it('should return initial array if no TBJNSHM is added', () => {
        const tBJNSHMCollection: ITBJNSHM[] = [{ jshcod: 'ABC' }];
        expectedResult = service.addTBJNSHMToCollectionIfMissing(tBJNSHMCollection, undefined, null);
        expect(expectedResult).toEqual(tBJNSHMCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
