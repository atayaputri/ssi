jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITBJNSHM, TBJNSHM } from '../tbjnshm.model';
import { TBJNSHMService } from '../service/tbjnshm.service';

import { TBJNSHMRoutingResolveService } from './tbjnshm-routing-resolve.service';

describe('TBJNSHM routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TBJNSHMRoutingResolveService;
  let service: TBJNSHMService;
  let resultTBJNSHM: ITBJNSHM | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TBJNSHMRoutingResolveService);
    service = TestBed.inject(TBJNSHMService);
    resultTBJNSHM = undefined;
  });

  describe('resolve', () => {
    it('should return ITBJNSHM returned by find', () => {
      // GIVEN
      service.find = jest.fn(jshcod => of(new HttpResponse({ body: { jshcod } })));
      mockActivatedRouteSnapshot.params = { jshcod: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBJNSHM = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBJNSHM).toEqual({ jshcod: 'ABC' });
    });

    it('should return new ITBJNSHM if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBJNSHM = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTBJNSHM).toEqual(new TBJNSHM());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TBJNSHM })));
      mockActivatedRouteSnapshot.params = { jshcod: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTBJNSHM = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTBJNSHM).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
