/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { RegimeComponent } from '../../../../../../main/webapp/app/entities/regime/regime.component';
import { RegimeService } from '../../../../../../main/webapp/app/entities/regime/regime.service';
import { Regime } from '../../../../../../main/webapp/app/entities/regime/regime.model';

describe('Component Tests', () => {

    describe('Regime Management Component', () => {
        let comp: RegimeComponent;
        let fixture: ComponentFixture<RegimeComponent>;
        let service: RegimeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [RegimeComponent],
                providers: [
                    RegimeService
                ]
            })
            .overrideTemplate(RegimeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RegimeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RegimeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Regime(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.regimes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
