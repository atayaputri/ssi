import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IMFSHM, MFSHM } from '../mfshm.model';

import { MFSHMService } from './mfshm.service';

describe('MFSHM Service', () => {
  let service: MFSHMService;
  let httpMock: HttpTestingController;
  let elemDefault: IMFSHM;
  let expectedResult: IMFSHM | IMFSHM[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MFSHMService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      id: 0,
      shsts: 'AAAAAAA',
      shfr: 0,
      shto: 0,
      shjshm: 0,
      shbat: 0,
      shseq: 0,
      shref: 'AAAAAAA',
      shdis: currentDate,
      shlmd: currentDate,
      shuid: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          shdis: currentDate.format(DATE_FORMAT),
          shlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a MFSHM', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          shdis: currentDate.format(DATE_FORMAT),
          shlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          shdis: currentDate,
          shlmd: currentDate,
        },
        returnedFromService
      );

      service.create(new MFSHM()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MFSHM', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          shsts: 'BBBBBB',
          shfr: 1,
          shto: 1,
          shjshm: 1,
          shbat: 1,
          shseq: 1,
          shref: 'BBBBBB',
          shdis: currentDate.format(DATE_FORMAT),
          shlmd: currentDate.format(DATE_FORMAT),
          shuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          shdis: currentDate,
          shlmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MFSHM', () => {
      const patchObject = Object.assign(
        {
          shjshm: 1,
          shbat: 1,
          shdis: currentDate.format(DATE_FORMAT),
          shuid: 'BBBBBB',
        },
        new MFSHM()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          shdis: currentDate,
          shlmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MFSHM', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          shsts: 'BBBBBB',
          shfr: 1,
          shto: 1,
          shjshm: 1,
          shbat: 1,
          shseq: 1,
          shref: 'BBBBBB',
          shdis: currentDate.format(DATE_FORMAT),
          shlmd: currentDate.format(DATE_FORMAT),
          shuid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          shdis: currentDate,
          shlmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a MFSHM', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMFSHMToCollectionIfMissing', () => {
      it('should add a MFSHM to an empty array', () => {
        const mFSHM: IMFSHM = { id: 123 };
        expectedResult = service.addMFSHMToCollectionIfMissing([], mFSHM);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mFSHM);
      });

      it('should not add a MFSHM to an array that contains it', () => {
        const mFSHM: IMFSHM = { id: 123 };
        const mFSHMCollection: IMFSHM[] = [
          {
            ...mFSHM,
          },
          { id: 456 },
        ];
        expectedResult = service.addMFSHMToCollectionIfMissing(mFSHMCollection, mFSHM);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MFSHM to an array that doesn't contain it", () => {
        const mFSHM: IMFSHM = { id: 123 };
        const mFSHMCollection: IMFSHM[] = [{ id: 456 }];
        expectedResult = service.addMFSHMToCollectionIfMissing(mFSHMCollection, mFSHM);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mFSHM);
      });

      it('should add only unique MFSHM to an array', () => {
        const mFSHMArray: IMFSHM[] = [{ id: 123 }, { id: 456 }, { id: 69309 }];
        const mFSHMCollection: IMFSHM[] = [{ id: 123 }];
        expectedResult = service.addMFSHMToCollectionIfMissing(mFSHMCollection, ...mFSHMArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mFSHM: IMFSHM = { id: 123 };
        const mFSHM2: IMFSHM = { id: 456 };
        expectedResult = service.addMFSHMToCollectionIfMissing([], mFSHM, mFSHM2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mFSHM);
        expect(expectedResult).toContain(mFSHM2);
      });

      it('should accept null and undefined values', () => {
        const mFSHM: IMFSHM = { id: 123 };
        expectedResult = service.addMFSHMToCollectionIfMissing([], null, mFSHM, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mFSHM);
      });

      it('should return initial array if no MFSHM is added', () => {
        const mFSHMCollection: IMFSHM[] = [{ id: 123 }];
        expectedResult = service.addMFSHMToCollectionIfMissing(mFSHMCollection, undefined, null);
        expect(expectedResult).toEqual(mFSHMCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
