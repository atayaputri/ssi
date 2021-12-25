import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITBPART, TBPART } from '../tbpart.model';

import { TBPARTService } from './tbpart.service';

describe('TBPART Service', () => {
  let service: TBPARTService;
  let httpMock: HttpTestingController;
  let elemDefault: ITBPART;
  let expectedResult: ITBPART | ITBPART[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TBPARTService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      tpasts: 'AAAAAAA',
      tpacode: 'AAAAAAA',
      tpanam: 'AAAAAAA',
      tparek: 'AAAAAAA',
      tpadis: currentDate,
      tpalmd: currentDate,
      tpauid: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          tpadis: currentDate.format(DATE_FORMAT),
          tpalmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TBPART', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          tpadis: currentDate.format(DATE_FORMAT),
          tpalmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          tpadis: currentDate,
          tpalmd: currentDate,
        },
        returnedFromService
      );

      service.create(new TBPART()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TBPART', () => {
      const returnedFromService = Object.assign(
        {
          tpasts: 'BBBBBB',
          tpacode: 'BBBBBB',
          tpanam: 'BBBBBB',
          tparek: 'BBBBBB',
          tpadis: currentDate.format(DATE_FORMAT),
          tpalmd: currentDate.format(DATE_FORMAT),
          tpauid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          tpadis: currentDate,
          tpalmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TBPART', () => {
      const patchObject = Object.assign(
        {
          tpasts: 'BBBBBB',
          tpanam: 'BBBBBB',
          tparek: 'BBBBBB',
        },
        new TBPART()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          tpadis: currentDate,
          tpalmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TBPART', () => {
      const returnedFromService = Object.assign(
        {
          tpasts: 'BBBBBB',
          tpacode: 'BBBBBB',
          tpanam: 'BBBBBB',
          tparek: 'BBBBBB',
          tpadis: currentDate.format(DATE_FORMAT),
          tpalmd: currentDate.format(DATE_FORMAT),
          tpauid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          tpadis: currentDate,
          tpalmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TBPART', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTBPARTToCollectionIfMissing', () => {
      it('should add a TBPART to an empty array', () => {
        const tBPART: ITBPART = { tpacode: 'ABC' };
        expectedResult = service.addTBPARTToCollectionIfMissing([], tBPART);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBPART);
      });

      it('should not add a TBPART to an array that contains it', () => {
        const tBPART: ITBPART = { tpacode: 'ABC' };
        const tBPARTCollection: ITBPART[] = [
          {
            ...tBPART,
          },
          { tpacode: 'CBA' },
        ];
        expectedResult = service.addTBPARTToCollectionIfMissing(tBPARTCollection, tBPART);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TBPART to an array that doesn't contain it", () => {
        const tBPART: ITBPART = { tpacode: 'ABC' };
        const tBPARTCollection: ITBPART[] = [{ tpacode: 'CBA' }];
        expectedResult = service.addTBPARTToCollectionIfMissing(tBPARTCollection, tBPART);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBPART);
      });

      it('should add only unique TBPART to an array', () => {
        const tBPARTArray: ITBPART[] = [{ tpacode: 'ABC' }, { tpacode: 'CBA' }, { tpacode: '1dcd8205-5' }];
        const tBPARTCollection: ITBPART[] = [{ tpacode: 'ABC' }];
        expectedResult = service.addTBPARTToCollectionIfMissing(tBPARTCollection, ...tBPARTArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tBPART: ITBPART = { tpacode: 'ABC' };
        const tBPART2: ITBPART = { tpacode: 'CBA' };
        expectedResult = service.addTBPARTToCollectionIfMissing([], tBPART, tBPART2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBPART);
        expect(expectedResult).toContain(tBPART2);
      });

      it('should accept null and undefined values', () => {
        const tBPART: ITBPART = { tpacode: 'ABC' };
        expectedResult = service.addTBPARTToCollectionIfMissing([], null, tBPART, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBPART);
      });

      it('should return initial array if no TBPART is added', () => {
        const tBPARTCollection: ITBPART[] = [{ tpacode: 'ABC' }];
        expectedResult = service.addTBPARTToCollectionIfMissing(tBPARTCollection, undefined, null);
        expect(expectedResult).toEqual(tBPARTCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
