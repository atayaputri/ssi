import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { StatusSKS } from 'app/entities/enumerations/status-sks.model';
import { IMAPSKS, MAPSKS } from '../mapsks.model';

import { MAPSKSService } from './mapsks.service';

describe('MAPSKS Service', () => {
  let service: MAPSKSService;
  let httpMock: HttpTestingController;
  let elemDefault: IMAPSKS;
  let expectedResult: IMAPSKS | IMAPSKS[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(MAPSKSService);
    httpMock = TestBed.inject(HttpTestingController);

    elemDefault = {
      id: 0,
      msksts: StatusSKS.A,
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

    it('should create a MAPSKS', () => {
      const returnedFromService = Object.assign(
        {
          id: 0,
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.create(new MAPSKS()).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a MAPSKS', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          msksts: 'BBBBBB',
        },
        elemDefault
      );

      const expected = Object.assign({}, returnedFromService);

      service.update(expected).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a MAPSKS', () => {
      const patchObject = Object.assign({}, new MAPSKS());

      const returnedFromService = Object.assign(patchObject, elemDefault);

      const expected = Object.assign({}, returnedFromService);

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of MAPSKS', () => {
      const returnedFromService = Object.assign(
        {
          id: 1,
          msksts: 'BBBBBB',
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

    it('should delete a MAPSKS', () => {
      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult);
    });

    describe('addMAPSKSToCollectionIfMissing', () => {
      it('should add a MAPSKS to an empty array', () => {
        const mAPSKS: IMAPSKS = { id: 123 };
        expectedResult = service.addMAPSKSToCollectionIfMissing([], mAPSKS);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mAPSKS);
      });

      it('should not add a MAPSKS to an array that contains it', () => {
        const mAPSKS: IMAPSKS = { id: 123 };
        const mAPSKSCollection: IMAPSKS[] = [
          {
            ...mAPSKS,
          },
          { id: 456 },
        ];
        expectedResult = service.addMAPSKSToCollectionIfMissing(mAPSKSCollection, mAPSKS);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a MAPSKS to an array that doesn't contain it", () => {
        const mAPSKS: IMAPSKS = { id: 123 };
        const mAPSKSCollection: IMAPSKS[] = [{ id: 456 }];
        expectedResult = service.addMAPSKSToCollectionIfMissing(mAPSKSCollection, mAPSKS);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mAPSKS);
      });

      it('should add only unique MAPSKS to an array', () => {
        const mAPSKSArray: IMAPSKS[] = [{ id: 123 }, { id: 456 }, { id: 92342 }];
        const mAPSKSCollection: IMAPSKS[] = [{ id: 123 }];
        expectedResult = service.addMAPSKSToCollectionIfMissing(mAPSKSCollection, ...mAPSKSArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const mAPSKS: IMAPSKS = { id: 123 };
        const mAPSKS2: IMAPSKS = { id: 456 };
        expectedResult = service.addMAPSKSToCollectionIfMissing([], mAPSKS, mAPSKS2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(mAPSKS);
        expect(expectedResult).toContain(mAPSKS2);
      });

      it('should accept null and undefined values', () => {
        const mAPSKS: IMAPSKS = { id: 123 };
        expectedResult = service.addMAPSKSToCollectionIfMissing([], null, mAPSKS, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(mAPSKS);
      });

      it('should return initial array if no MAPSKS is added', () => {
        const mAPSKSCollection: IMAPSKS[] = [{ id: 123 }];
        expectedResult = service.addMAPSKSToCollectionIfMissing(mAPSKSCollection, undefined, null);
        expect(expectedResult).toEqual(mAPSKSCollection);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
