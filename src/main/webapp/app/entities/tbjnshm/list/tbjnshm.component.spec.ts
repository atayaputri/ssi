import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TBJNSHMService } from '../service/tbjnshm.service';

import { TBJNSHMComponent } from './tbjnshm.component';

describe('TBJNSHM Management Component', () => {
  let comp: TBJNSHMComponent;
  let fixture: ComponentFixture<TBJNSHMComponent>;
  let service: TBJNSHMService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBJNSHMComponent],
    })
      .overrideTemplate(TBJNSHMComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBJNSHMComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBJNSHMService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ jshcod: 'ABC' }],
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
    expect(comp.tBJNSHMS?.[0]).toEqual(expect.objectContaining({ jshcod: 'ABC' }));
  });
});
