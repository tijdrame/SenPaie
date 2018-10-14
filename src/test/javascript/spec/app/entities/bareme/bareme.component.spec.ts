/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { BaremeComponent } from '../../../../../../main/webapp/app/entities/bareme/bareme.component';
import { BaremeService } from '../../../../../../main/webapp/app/entities/bareme/bareme.service';
import { Bareme } from '../../../../../../main/webapp/app/entities/bareme/bareme.model';

describe('Component Tests', () => {

    describe('Bareme Management Component', () => {
        let comp: BaremeComponent;
        let fixture: ComponentFixture<BaremeComponent>;
        let service: BaremeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [BaremeComponent],
                providers: [
                    BaremeService
                ]
            })
            .overrideTemplate(BaremeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BaremeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BaremeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Bareme(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.baremes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
