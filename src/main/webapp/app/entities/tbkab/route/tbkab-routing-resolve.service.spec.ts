jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITBKAB, TBKAB } from '../tbkab.model';
import { TBKABService } from '../service/tbkab.service';

import { TBKABRoutingResolveService } from './tbkab-routing-resolve.service';

describe('TBKAB routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TBKABRoutingResolveService;
  let service: TBKABService;
  let resultTBKAB: ITBKAB | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TBKABRoutingResolveService);
    service = TestBed.inject(TBKABService);
    resultTBKAB = undefined;
  });

  describe('resolve', () => {
    it('should return ITBKAB returned by find', () => {
      // GIVEN
      service.find = jest.fn(kabcod => of(new HttpResponse({ body: { kabcod } })));
      mockActivatedRouteSnapshot.params = { kabcod: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBKAB = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBKAB).toEqual({ kabcod: 'ABC' });
    });

    it('should return new ITBKAB if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBKAB = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTBKAB).toEqual(new TBKAB());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TBKAB })));
      mockActivatedRouteSnapshot.params = { kabcod: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBKAB = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBKAB).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
