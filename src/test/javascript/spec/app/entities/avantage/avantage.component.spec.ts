/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { AvantageComponent } from '../../../../../../main/webapp/app/entities/avantage/avantage.component';
import { AvantageService } from '../../../../../../main/webapp/app/entities/avantage/avantage.service';
import { Avantage } from '../../../../../../main/webapp/app/entities/avantage/avantage.model';

describe('Component Tests', () => {

    describe('Avantage Management Component', () => {
        let comp: AvantageComponent;
        let fixture: ComponentFixture<AvantageComponent>;
        let service: AvantageService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [AvantageComponent],
                providers: [
                    AvantageService
                ]
            })
            .overrideTemplate(AvantageComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AvantageComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AvantageService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Avantage(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.avantages[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
