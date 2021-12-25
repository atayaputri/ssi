jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { IMFHDR, MFHDR } from '../mfhdr.model';
import { MFHDRService } from '../service/mfhdr.service';

import { MFHDRRoutingResolveService } from './mfhdr-routing-resolve.service';

describe('MFHDR routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: MFHDRRoutingResolveService;
  let service: MFHDRService;
  let resultMFHDR: IMFHDR | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(MFHDRRoutingResolveService);
    service = TestBed.inject(MFHDRService);
    resultMFHDR = undefined;
  });

  describe('resolve', () => {
    it('should return IMFHDR returned by find', () => {
      // GIVEN
      service.find = jest.fn(hdno => of(new HttpResponse({ body: { hdno } })));
      mockActivatedRouteSnapshot.params = { hdno: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMFHDR = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMFHDR).toEqual({ hdno: 123 });
    });

    it('should return new IMFHDR if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMFHDR = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultMFHDR).toEqual(new MFHDR());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as MFHDR })));
      mockActivatedRouteSnapshot.params = { hdno: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultMFHDR = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultMFHDR).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
