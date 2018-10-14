/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { TypeContratComponent } from '../../../../../../main/webapp/app/entities/type-contrat/type-contrat.component';
import { TypeContratService } from '../../../../../../main/webapp/app/entities/type-contrat/type-contrat.service';
import { TypeContrat } from '../../../../../../main/webapp/app/entities/type-contrat/type-contrat.model';

describe('Component Tests', () => {

    describe('TypeContrat Management Component', () => {
        let comp: TypeContratComponent;
        let fixture: ComponentFixture<TypeContratComponent>;
        let service: TypeContratService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [TypeContratComponent],
                providers: [
                    TypeContratService
                ]
            })
            .overrideTemplate(TypeContratComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeContratComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeContratService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TypeContrat(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.typeContrats[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
