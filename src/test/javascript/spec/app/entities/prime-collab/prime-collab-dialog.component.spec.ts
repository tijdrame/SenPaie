/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenPaieTestModule } from '../../../test.module';
import { PrimeCollabDialogComponent } from '../../../../../../main/webapp/app/entities/prime-collab/prime-collab-dialog.component';
import { PrimeCollabService } from '../../../../../../main/webapp/app/entities/prime-collab/prime-collab.service';
import { PrimeCollab } from '../../../../../../main/webapp/app/entities/prime-collab/prime-collab.model';
import { CollaborateurService } from '../../../../../../main/webapp/app/entities/collaborateur';
import { PrimeService } from '../../../../../../main/webapp/app/entities/prime';

describe('Component Tests', () => {

    describe('PrimeCollab Management Dialog Component', () => {
        let comp: PrimeCollabDialogComponent;
        let fixture: ComponentFixture<PrimeCollabDialogComponent>;
        let service: PrimeCollabService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [PrimeCollabDialogComponent],
                providers: [
                    CollaborateurService,
                    PrimeService,
                    PrimeCollabService
                ]
            })
            .overrideTemplate(PrimeCollabDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PrimeCollabDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PrimeCollabService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PrimeCollab(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.primeCollab = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'primeCollabListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new PrimeCollab();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.primeCollab = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'primeCollabListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
