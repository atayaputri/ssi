jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITBHDR, TBHDR } from '../tbhdr.model';
import { TBHDRService } from '../service/tbhdr.service';

import { TBHDRRoutingResolveService } from './tbhdr-routing-resolve.service';

describe('TBHDR routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TBHDRRoutingResolveService;
  let service: TBHDRService;
  let resultTBHDR: ITBHDR | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TBHDRRoutingResolveService);
    service = TestBed.inject(TBHDRService);
    resultTBHDR = undefined;
  });

  describe('resolve', () => {
    it('should return ITBHDR returned by find', () => {
      // GIVEN
      service.find = jest.fn(thno => of(new HttpResponse({ body: { thno } })));
      mockActivatedRouteSnapshot.params = { thno: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBHDR = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTBHDR).toEqual({ thno: 123 });
    });

    it('should return new ITBHDR if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBHDR = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTBHDR).toEqual(new TBHDR());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TBHDR })));
      mockActivatedRouteSnapshot.params = { thno: 123 };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBHDR = result;
      });

      // THEN
      expect(service.find).toBeCalledWith(123);
      expect(resultTBHDR).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
