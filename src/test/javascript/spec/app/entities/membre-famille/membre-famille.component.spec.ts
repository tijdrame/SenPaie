/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { MembreFamilleComponent } from '../../../../../../main/webapp/app/entities/membre-famille/membre-famille.component';
import { MembreFamilleService } from '../../../../../../main/webapp/app/entities/membre-famille/membre-famille.service';
import { MembreFamille } from '../../../../../../main/webapp/app/entities/membre-famille/membre-famille.model';

describe('Component Tests', () => {

    describe('MembreFamille Management Component', () => {
        let comp: MembreFamilleComponent;
        let fixture: ComponentFixture<MembreFamilleComponent>;
        let service: MembreFamilleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [MembreFamilleComponent],
                providers: [
                    MembreFamilleService
                ]
            })
            .overrideTemplate(MembreFamilleComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MembreFamilleComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MembreFamilleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new MembreFamille(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.membreFamilles[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
