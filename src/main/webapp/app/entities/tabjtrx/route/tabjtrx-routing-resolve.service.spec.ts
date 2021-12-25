jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ITABJTRX, TABJTRX } from '../tabjtrx.model';
import { TABJTRXService } from '../service/tabjtrx.service';

import { TABJTRXRoutingResolveService } from './tabjtrx-routing-resolve.service';

describe('TABJTRX routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let routingResolveService: TABJTRXRoutingResolveService;
  let service: TABJTRXService;
  let resultTABJTRX: ITABJTRX | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [Router, ActivatedRouteSnapshot],
    });
    mockRouter = TestBed.inject(Router);
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
    routingResolveService = TestBed.inject(TABJTRXRoutingResolveService);
    service = TestBed.inject(TABJTRXService);
    resultTABJTRX = undefined;
  });

  describe('resolve', () => {
    it('should return ITABJTRX returned by find', () => {
      // GIVEN
      service.find = jest.fn(jtjntx => of(new HttpResponse({ body: { jtjntx } })));
      mockActivatedRouteSnapshot.params = { jtjntx: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTABJTRX = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTABJTRX).toEqual({ jtjntx: 'ABC' });
    });

    it('should return new ITABJTRX if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTABJTRX = result;
      });

      // THEN
      expect(service.find).not.toBeCalled();
      expect(resultTABJTRX).toEqual(new TABJTRX());
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse({ body: null as unknown as TABJTRX })));
      mockActivatedRouteSnapshot.params = { jtjntx: 'ABC' };

      // WHEN
      routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
        resultTABJTRX = result;
      });

      // THEN
      expect(service.find).toBeCalledWith('ABC');
      expect(resultTABJTRX).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
