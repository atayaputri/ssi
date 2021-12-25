import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { HolderGroup } from 'app/entities/enumerations/holder-group.model';
import { Citizenship } from 'app/entities/enumerations/citizenship.model';
import { IMFHDR, MFHDR } from '../mfhdr.model';

import { MFHDRService } from './mfhdr.service';

describe('MFHDR Service', () => {
  let service: MFHDRService;
  let httpMock: HttpTestingController;
  let elemDefault: IMFHDR;
  let expectedResult: IMFHDR | IMFHDR[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MFHDRService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      hdsts: 'AAAAAAA',
      hdno: 0,
      hdsid: 'AAAAAAA',
      hdnm1: 'AAAAAAA',
      hdnm2: 'AAAAAAA',
      hdal1: 'AAAAAAA',
      hdal2: 'AAAAAAA',
      hdjsh: 0,
      hdinco: HolderGroup.I,
      hdkwn: Citizenship.I,
      hdktp: 'AAAAAAA',
      hdnpwp: 'AAAAAAA',
      hdsiup: 'AAAAAAA',
      hdtax: 0,
      hddis: currentDate,
      hdlmd: currentDate,
      hduid: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          hddis: currentDate.format(DATE_FORMAT),
          hdlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a MFHDR', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
          hddis: currentDate.format(DATE_FORMAT),
          hdlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          hddis: currentDate,
          hdlmd: currentDate,
        },
        returnedFromService
      );

      service.create(new MFHDR()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MFHDR', () => {
      const returnedFromService = Object.assign(
        {
          hdsts: 'BBBBBB',
          hdno: 1,
          hdsid: 'BBBBBB',
          hdnm1: 'BBBBBB',
          hdnm2: 'BBBBBB',
          hdal1: 'BBBBBB',
          hdal2: 'BBBBBB',
          hdjsh: 1,
          hdinco: 'BBBBBB',
          hdkwn: 'BBBBBB',
          hdktp: 'BBBBBB',
          hdnpwp: 'BBBBBB',
          hdsiup: 'BBBBBB',
          hdtax: 1,
          hddis: currentDate.format(DATE_FORMAT),
          hdlmd: currentDate.format(DATE_FORMAT),
          hduid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          hddis: currentDate,
          hdlmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MFHDR', () => {
      const patchObject = Object.assign(
        {
          hdsts: 'BBBBBB',
          hdnm2: 'BBBBBB',
          hdal1: 'BBBBBB',
          hdal2: 'BBBBBB',
          hdjsh: 1,
          hdinco: 'BBBBBB',
          hdkwn: 'BBBBBB',
          hdnpwp: 'BBBBBB',
          hdsiup: 'BBBBBB',
          hdtax: 1,
          hddis: currentDate.format(DATE_FORMAT),
          hduid: 'BBBBBB',
        },
        new MFHDR()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          hddis: currentDate,
          hdlmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MFHDR', () => {
      const returnedFromService = Object.assign(
        {
          hdsts: 'BBBBBB',
          hdno: 1,
          hdsid: 'BBBBBB',
          hdnm1: 'BBBBBB',
          hdnm2: 'BBBBBB',
          hdal1: 'BBBBBB',
          hdal2: 'BBBBBB',
          hdjsh: 1,
          hdinco: 'BBBBBB',
          hdkwn: 'BBBBBB',
          hdktp: 'BBBBBB',
          hdnpwp: 'BBBBBB',
          hdsiup: 'BBBBBB',
          hdtax: 1,
          hddis: currentDate.format(DATE_FORMAT),
          hdlmd: currentDate.format(DATE_FORMAT),
          hduid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          hddis: currentDate,
          hdlmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a MFHDR', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMFHDRToCollectionIfMissing', () => {
      it('should add a MFHDR to an empty array', () => {
        const mFHDR: IMFHDR = { hdno: 123 };
        expectedResult = service.addMFHDRToCollectionIfMissing([], mFHDR);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mFHDR);
      });

      it('should not add a MFHDR to an array that contains it', () => {
        const mFHDR: IMFHDR = { hdno: 123 };
        const mFHDRCollection: IMFHDR[] = [
          {
            ...mFHDR,
          },
          { hdno: 456 },
        ];
        expectedResult = service.addMFHDRToCollectionIfMissing(mFHDRCollection, mFHDR);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MFHDR to an array that doesn't contain it", () => {
        const mFHDR: IMFHDR = { hdno: 123 };
        const mFHDRCollection: IMFHDR[] = [{ hdno: 456 }];
        expectedResult = service.addMFHDRToCollectionIfMissing(mFHDRCollection, mFHDR);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mFHDR);
      });

      it('should add only unique MFHDR to an array', () => {
        const mFHDRArray: IMFHDR[] = [{ hdno: 123 }, { hdno: 456 }, { hdno: 88696 }];
        const mFHDRCollection: IMFHDR[] = [{ hdno: 123 }];
        expectedResult = service.addMFHDRToCollectionIfMissing(mFHDRCollection, ...mFHDRArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mFHDR: IMFHDR = { hdno: 123 };
        const mFHDR2: IMFHDR = { hdno: 456 };
        expectedResult = service.addMFHDRToCollectionIfMissing([], mFHDR, mFHDR2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mFHDR);
        expect(expectedResult).toContain(mFHDR2);
      });

      it('should accept null and undefined values', () => {
        const mFHDR: IMFHDR = { hdno: 123 };
        expectedResult = service.addMFHDRToCollectionIfMissing([], null, mFHDR, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mFHDR);
      });

      it('should return initial array if no MFHDR is added', () => {
        const mFHDRCollection: IMFHDR[] = [{ hdno: 123 }];
        expectedResult = service.addMFHDRToCollectionIfMissing(mFHDRCollection, undefined, null);
        expect(expectedResult).toEqual(mFHDRCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
