import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITBJNPS, TBJNPS } from '../tbjnps.model';

import { TBJNPSService } from './tbjnps.service';

describe('TBJNPS Service', () => {
  let service: TBJNPSService;
  let httpMock: HttpTestingController;
  let elemDefault: ITBJNPS;
  let expectedResult: ITBJNPS | ITBJNPS[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TBJNPSService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      jpssts: 'AAAAAAA',
      jpscod: 'AAAAAAA',
      jpsnam: 'AAAAAAA',
      jpslmd: currentDate,
      jpsuid: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          jpslmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TBJNPS', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          jpslmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          jpslmd: currentDate,
        },
        returnedFromService
      );

      service.create(new TBJNPS()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TBJNPS', () => {
      const returnedFromService = Object.assign(
        {
          jpssts: 'BBBBBB',
          jpscod: 'BBBBBB',
          jpsnam: 'BBBBBB',
          jpslmd: currentDate.format(DATE_FORMAT),
          jpsuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          jpslmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TBJNPS', () => {
      const patchObject = Object.assign(
        {
          jpssts: 'BBBBBB',
          jpsnam: 'BBBBBB',
          jpslmd: currentDate.format(DATE_FORMAT),
        },
        new TBJNPS()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          jpslmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TBJNPS', () => {
      const returnedFromService = Object.assign(
        {
          jpssts: 'BBBBBB',
          jpscod: 'BBBBBB',
          jpsnam: 'BBBBBB',
          jpslmd: currentDate.format(DATE_FORMAT),
          jpsuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          jpslmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TBJNPS', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTBJNPSToCollectionIfMissing', () => {
      it('should add a TBJNPS to an empty array', () => {
        const tBJNPS: ITBJNPS = { jpscod: 'ABC' };
        expectedResult = service.addTBJNPSToCollectionIfMissing([], tBJNPS);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBJNPS);
      });

      it('should not add a TBJNPS to an array that contains it', () => {
        const tBJNPS: ITBJNPS = { jpscod: 'ABC' };
        const tBJNPSCollection: ITBJNPS[] = [
          {
            ...tBJNPS,
          },
          { jpscod: 'CBA' },
        ];
        expectedResult = service.addTBJNPSToCollectionIfMissing(tBJNPSCollection, tBJNPS);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TBJNPS to an array that doesn't contain it", () => {
        const tBJNPS: ITBJNPS = { jpscod: 'ABC' };
        const tBJNPSCollection: ITBJNPS[] = [{ jpscod: 'CBA' }];
        expectedResult = service.addTBJNPSToCollectionIfMissing(tBJNPSCollection, tBJNPS);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBJNPS);
      });

      it('should add only unique TBJNPS to an array', () => {
        const tBJNPSArray: ITBJNPS[] = [{ jpscod: 'ABC' }, { jpscod: 'CBA' }, { jpscod: 'e' }];
        const tBJNPSCollection: ITBJNPS[] = [{ jpscod: 'ABC' }];
        expectedResult = service.addTBJNPSToCollectionIfMissing(tBJNPSCollection, ...tBJNPSArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tBJNPS: ITBJNPS = { jpscod: 'ABC' };
        const tBJNPS2: ITBJNPS = { jpscod: 'CBA' };
        expectedResult = service.addTBJNPSToCollectionIfMissing([], tBJNPS, tBJNPS2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBJNPS);
        expect(expectedResult).toContain(tBJNPS2);
      });

      it('should accept null and undefined values', () => {
        const tBJNPS: ITBJNPS = { jpscod: 'ABC' };
        expectedResult = service.addTBJNPSToCollectionIfMissing([], null, tBJNPS, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBJNPS);
      });

      it('should return initial array if no TBJNPS is added', () => {
        const tBJNPSCollection: ITBJNPS[] = [{ jpscod: 'ABC' }];
        expectedResult = service.addTBJNPSToCollectionIfMissing(tBJNPSCollection, undefined, null);
        expect(expectedResult).toEqual(tBJNPSCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
