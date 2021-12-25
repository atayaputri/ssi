import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TBKABService } from '../service/tbkab.service';

import { TBKABComponent } from './tbkab.component';

describe('TBKAB Management Component', () => {
  let comp: TBKABComponent;
  let fixture: ComponentFixture<TBKABComponent>;
  let service: TBKABService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBKABComponent],
    })
      .overrideTemplate(TBKABComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBKABComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBKABService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ kabcod: 'ABC' }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.tBKABS?.[0]).toEqual(expect.objectContaining({ kabcod: 'ABC' }));
  });
});
