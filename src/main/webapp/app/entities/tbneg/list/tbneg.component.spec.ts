import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TBNEGService } from '../service/tbneg.service';

import { TBNEGComponent } from './tbneg.component';

describe('TBNEG Management Component', () => {
  let comp: TBNEGComponent;
  let fixture: ComponentFixture<TBNEGComponent>;
  let service: TBNEGService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBNEGComponent],
    })
      .overrideTemplate(TBNEGComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TBNEGComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBNEGService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ negcod: 'ABC' }],
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
    expect(comp.tBNEGS?.[0]).toEqual(expect.objectContaining({ negcod: 'ABC' }));
  });
});
