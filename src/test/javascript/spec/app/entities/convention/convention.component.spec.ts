/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { ConventionComponent } from '../../../../../../main/webapp/app/entities/convention/convention.component';
import { ConventionService } from '../../../../../../main/webapp/app/entities/convention/convention.service';
import { Convention } from '../../../../../../main/webapp/app/entities/convention/convention.model';

describe('Component Tests', () => {

    describe('Convention Management Component', () => {
        let comp: ConventionComponent;
        let fixture: ComponentFixture<ConventionComponent>;
        let service: ConventionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [ConventionComponent],
                providers: [
                    ConventionService
                ]
            })
            .overrideTemplate(ConventionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ConventionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConventionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Convention(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.conventions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
