import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITBNEG, TBNEG } from '../tbneg.model';

import { TBNEGService } from './tbneg.service';

describe('TBNEG Service', () => {
  let service: TBNEGService;
  let httpMock: HttpTestingController;
  let elemDefault: ITBNEG;
  let expectedResult: ITBNEG | ITBNEG[] | boolean | null;
  let currentDate: dayjs.Dayjs;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(TBNEGService);
    httpMock = TestBed.inject(HttpTestingController);
    currentDate = dayjs();

    elemDefault = {
      negsts: 'AAAAAAA',
      negcod: 'AAAAAAA',
      negnam: 'AAAAAAA',
      negtax: 0,
      neglmd: currentDate,
      neguid: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign(
        {
          neglmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a TBNEG', () => {
      const returnedFromService = Object.assign(
        {
          id: 'ID',
          neglmd: currentDate.format(DATE_FORMAT),
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          neglmd: currentDate,
        },
        returnedFromService
      );

      service.create(new TBNEG()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a TBNEG', () => {
      const returnedFromService = Object.assign(
        {
          negsts: 'BBBBBB',
          negcod: 'BBBBBB',
          negnam: 'BBBBBB',
          negtax: 1,
          neglmd: currentDate.format(DATE_FORMAT),
          neguid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          neglmd: currentDate,
        },
        returnedFromService
      );

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a TBNEG', () => {
      const patchObject = Object.assign(
        {
          negtax: 1,
          neglmd: currentDate.format(DATE_FORMAT),
        },
        new TBNEG()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign(
        {
          neglmd: currentDate,
        },
        returnedFromService
      );

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of TBNEG', () => {
      const returnedFromService = Object.assign(
        {
          negsts: 'BBBBBB',
          negcod: 'BBBBBB',
          negnam: 'BBBBBB',
          negtax: 1,
          neglmd: currentDate.format(DATE_FORMAT),
          neguid: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign(
        {
          neglmd: currentDate,
        },
        returnedFromService
      );

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a TBNEG', () => {
      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addTBNEGToCollectionIfMissing', () => {
      it('should add a TBNEG to an empty array', () => {
        const tBNEG: ITBNEG = { negcod: 'ABC' };
        expectedResult = service.addTBNEGToCollectionIfMissing([], tBNEG);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBNEG);
      });

      it('should not add a TBNEG to an array that contains it', () => {
        const tBNEG: ITBNEG = { negcod: 'ABC' };
        const tBNEGCollection: ITBNEG[] = [
          {
            ...tBNEG,
          },
          { negcod: 'CBA' },
        ];
        expectedResult = service.addTBNEGToCollectionIfMissing(tBNEGCollection, tBNEG);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a TBNEG to an array that doesn't contain it", () => {
        const tBNEG: ITBNEG = { negcod: 'ABC' };
        const tBNEGCollection: ITBNEG[] = [{ negcod: 'CBA' }];
        expectedResult = service.addTBNEGToCollectionIfMissing(tBNEGCollection, tBNEG);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBNEG);
      });

      it('should add only unique TBNEG to an array', () => {
        const tBNEGArray: ITBNEG[] = [{ negcod: 'ABC' }, { negcod: 'CBA' }, { negcod: '7413' }];
        const tBNEGCollection: ITBNEG[] = [{ negcod: 'ABC' }];
        expectedResult = service.addTBNEGToCollectionIfMissing(tBNEGCollection, ...tBNEGArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const tBNEG: ITBNEG = { negcod: 'ABC' };
        const tBNEG2: ITBNEG = { negcod: 'CBA' };
        expectedResult = service.addTBNEGToCollectionIfMissing([], tBNEG, tBNEG2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(tBNEG);
        expect(expectedResult).toContain(tBNEG2);
      });

      it('should accept null and undefined values', () => {
        const tBNEG: ITBNEG = { negcod: 'ABC' };
        expectedResult = service.addTBNEGToCollectionIfMissing([], null, tBNEG, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(tBNEG);
      });

      it('should return initial array if no TBNEG is added', () => {
        const tBNEGCollection: ITBNEG[] = [{ negcod: 'ABC' }];
        expectedResult = service.addTBNEGToCollectionIfMissing(tBNEGCollection, undefined, null);
        expect(expectedResult).toEqual(tBNEGCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
