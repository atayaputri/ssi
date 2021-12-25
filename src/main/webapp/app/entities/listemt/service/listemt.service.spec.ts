import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ILISTEMT, LISTEMT } from '../listemt.model';

import { LISTEMTService } from './listemt.service';

describe('LISTEMT Service', () => {
  let service: LISTEMTService;
  let httpMock: HttpTestingController;
  let elemDefault: ILISTEMT;
  let expectedResult: ILISTEMT | ILISTEMT[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(LISTEMTService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      liscode: 'AAAAAAA',
      lisnam: 'AAAAAAA',
      lisdir: 'AAAAAAA',
    };
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = Object.assign({}, elemDefault);

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(elemDefault);
    });

    it('should create a LISTEMT', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new LISTEMT()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a LISTEMT', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          liscode: 'BBBBBB',
          lisnam: 'BBBBBB',
          lisdir: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a LISTEMT', () => {
      const patchObject = Object.assign(
        {
          liscode: 'BBBBBB',
          lisdir: 'BBBBBB',
        },
        new LISTEMT()
      );

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of LISTEMT', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          liscode: 'BBBBBB',
          lisnam: 'BBBBBB',
          lisdir: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toContainEqual(expected);
    });

    it('should delete a LISTEMT', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addLISTEMTToCollectionIfMissing', () => {
      it('should add a LISTEMT to an empty array', () => {
        const lISTEMT: ILISTEMT = { id: 123 };
        expectedResult = service.addLISTEMTToCollectionIfMissing([], lISTEMT);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lISTEMT);
      });

      it('should not add a LISTEMT to an array that contains it', () => {
        const lISTEMT: ILISTEMT = { id: 123 };
        const lISTEMTCollection: ILISTEMT[] = [
          {
            ...lISTEMT,
          },
          { id: 456 },
        ];
        expectedResult = service.addLISTEMTToCollectionIfMissing(lISTEMTCollection, lISTEMT);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a LISTEMT to an array that doesn't contain it", () => {
        const lISTEMT: ILISTEMT = { id: 123 };
        const lISTEMTCollection: ILISTEMT[] = [{ id: 456 }];
        expectedResult = service.addLISTEMTToCollectionIfMissing(lISTEMTCollection, lISTEMT);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lISTEMT);
      });

      it('should add only unique LISTEMT to an array', () => {
        const lISTEMTArray: ILISTEMT[] = [{ id: 123 }, { id: 456 }, { id: 83221 }];
        const lISTEMTCollection: ILISTEMT[] = [{ id: 123 }];
        expectedResult = service.addLISTEMTToCollectionIfMissing(lISTEMTCollection, ...lISTEMTArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const lISTEMT: ILISTEMT = { id: 123 };
        const lISTEMT2: ILISTEMT = { id: 456 };
        expectedResult = service.addLISTEMTToCollectionIfMissing([], lISTEMT, lISTEMT2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(lISTEMT);
        expect(expectedResult).toContain(lISTEMT2);
      });

      it('should accept null and undefined values', () => {
        const lISTEMT: ILISTEMT = { id: 123 };
        expectedResult = service.addLISTEMTToCollectionIfMissing([], null, lISTEMT, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(lISTEMT);
      });

      it('should return initial array if no LISTEMT is added', () => {
        const lISTEMTCollection: ILISTEMT[] = [{ id: 123 }];
        expectedResult = service.addLISTEMTToCollectionIfMissing(lISTEMTCollection, undefined, null);
        expect(expectedResult).toEqual(lISTEMTCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
