import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITABJTRX, TABJTRX } from '../tabjtrx.model';

import { TABJTRXService } from './tabjtrx.service';

describe('TABJTRX Service', () => {
  let service: TABJTRXService;
  let httpMock: HttpTestingController;
  let elemDefault: ITABJTRX;
  let expectedResult: ITABJTRX | ITABJTRX[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TABJTRXService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      jtsts: 'AAAAAAA',
      jtjntx: 'AAAAAAA',
      jtdesc: 'AAAAAAA',
      jtsdes: 'AAAAAAA',
      jtlmd: currentDate,
      jtouid: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          jtlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TABJTRX', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          jtlmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          jtlmd: currentDate,
        },
        returnedFromService
      );

      service.create(new TABJTRX()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TABJTRX', () => {
      const returnedFromService = Object.assign(
        {
          jtsts: 'BBBBBB',
          jtjntx: 'BBBBBB',
          jtdesc: 'BBBBBB',
          jtsdes: 'BBBBBB',
          jtlmd: currentDate.format(DATE_FORMAT),
          jtouid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          jtlmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TABJTRX', () => {
      const patchObject = Object.assign(
        {
          jtsts: 'BBBBBB',
          jtdesc: 'BBBBBB',
          jtsdes: 'BBBBBB',
        },
        new TABJTRX()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          jtlmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TABJTRX', () => {
      const returnedFromService = Object.assign(
        {
          jtsts: 'BBBBBB',
          jtjntx: 'BBBBBB',
          jtdesc: 'BBBBBB',
          jtsdes: 'BBBBBB',
          jtlmd: currentDate.format(DATE_FORMAT),
          jtouid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          jtlmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TABJTRX', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTABJTRXToCollectionIfMissing', () => {
      it('should add a TABJTRX to an empty array', () => {
        const tABJTRX: ITABJTRX = { jtjntx: 'ABC' };
        expectedResult = service.addTABJTRXToCollectionIfMissing([], tABJTRX);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tABJTRX);
      });

      it('should not add a TABJTRX to an array that contains it', () => {
        const tABJTRX: ITABJTRX = { jtjntx: 'ABC' };
        const tABJTRXCollection: ITABJTRX[] = [
          {
            ...tABJTRX,
          },
          { jtjntx: 'CBA' },
        ];
        expectedResult = service.addTABJTRXToCollectionIfMissing(tABJTRXCollection, tABJTRX);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TABJTRX to an array that doesn't contain it", () => {
        const tABJTRX: ITABJTRX = { jtjntx: 'ABC' };
        const tABJTRXCollection: ITABJTRX[] = [{ jtjntx: 'CBA' }];
        expectedResult = service.addTABJTRXToCollectionIfMissing(tABJTRXCollection, tABJTRX);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tABJTRX);
      });

      it('should add only unique TABJTRX to an array', () => {
        const tABJTRXArray: ITABJTRX[] = [{ jtjntx: 'ABC' }, { jtjntx: 'CBA' }, { jtjntx: '7' }];
        const tABJTRXCollection: ITABJTRX[] = [{ jtjntx: 'ABC' }];
        expectedResult = service.addTABJTRXToCollectionIfMissing(tABJTRXCollection, ...tABJTRXArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tABJTRX: ITABJTRX = { jtjntx: 'ABC' };
        const tABJTRX2: ITABJTRX = { jtjntx: 'CBA' };
        expectedResult = service.addTABJTRXToCollectionIfMissing([], tABJTRX, tABJTRX2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tABJTRX);
        expect(expectedResult).toContain(tABJTRX2);
      });

      it('should accept null and undefined values', () => {
        const tABJTRX: ITABJTRX = { jtjntx: 'ABC' };
        expectedResult = service.addTABJTRXToCollectionIfMissing([], null, tABJTRX, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tABJTRX);
      });

      it('should return initial array if no TABJTRX is added', () => {
        const tABJTRXCollection: ITABJTRX[] = [{ jtjntx: 'ABC' }];
        expectedResult = service.addTABJTRXToCollectionIfMissing(tABJTRXCollection, undefined, null);
        expect(expectedResult).toEqual(tABJTRXCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
