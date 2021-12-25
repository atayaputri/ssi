jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITBPROV, TBPROV } from '../tbprov.model';
import { TBPROVService } from '../service/tbprov.service';

import { TBPROVRoutingResolveService } from './tbprov-routing-resolve.service';

describe('TBPROV routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TBPROVRoutingResolveService;
  let service: TBPROVService;
  let resultTBPROV: ITBPROV | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TBPROVRoutingResolveService);
    service = TestBed.inject(TBPROVService);
    resultTBPROV = undefined;
  });

  describe('resolve', () => {
    it('should return ITBPROV returned by find', () => {
      // GIVEN
      service.find = jest.fn(provcod => of(new HttpResponse({ body: { provcod } })));
      mockActivatedRouteSnapshot.params = { provcod: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBPROV = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBPROV).toEqual({ provcod: 'ABC' });
    });

    it('should return new ITBPROV if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBPROV = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTBPROV).toEqual(new TBPROV());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TBPROV })));
      mockActivatedRouteSnapshot.params = { provcod: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBPROV = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBPROV).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
