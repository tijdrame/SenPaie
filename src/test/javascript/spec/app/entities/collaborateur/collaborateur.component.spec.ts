/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { CollaborateurComponent } from '../../../../../../main/webapp/app/entities/collaborateur/collaborateur.component';
import { CollaborateurService } from '../../../../../../main/webapp/app/entities/collaborateur/collaborateur.service';
import { Collaborateur } from '../../../../../../main/webapp/app/entities/collaborateur/collaborateur.model';

describe('Component Tests', () => {

    describe('Collaborateur Management Component', () => {
        let comp: CollaborateurComponent;
        let fixture: ComponentFixture<CollaborateurComponent>;
        let service: CollaborateurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [CollaborateurComponent],
                providers: [
                    CollaborateurService
                ]
            })
            .overrideTemplate(CollaborateurComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollaborateurComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollaborateurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Collaborateur(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.collaborateurs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
