/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenPaieTestModule } from '../../../test.module';
import { MembreFamilleDialogComponent } from '../../../../../../main/webapp/app/entities/membre-famille/membre-famille-dialog.component';
import { MembreFamilleService } from '../../../../../../main/webapp/app/entities/membre-famille/membre-famille.service';
import { MembreFamille } from '../../../../../../main/webapp/app/entities/membre-famille/membre-famille.model';
import { CollaborateurService } from '../../../../../../main/webapp/app/entities/collaborateur';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { TypeRelationService } from '../../../../../../main/webapp/app/entities/type-relation';
import { SexeService } from '../../../../../../main/webapp/app/entities/sexe';

describe('Component Tests', () => {

    describe('MembreFamille Management Dialog Component', () => {
        let comp: MembreFamilleDialogComponent;
        let fixture: ComponentFixture<MembreFamilleDialogComponent>;
        let service: MembreFamilleService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [MembreFamilleDialogComponent],
                providers: [
                    CollaborateurService,
                    UserService,
                    TypeRelationService,
                    SexeService,
                    MembreFamilleService
                ]
            })
            .overrideTemplate(MembreFamilleDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MembreFamilleDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MembreFamilleService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MembreFamille(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.membreFamille = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'membreFamilleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MembreFamille();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.membreFamille = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'membreFamilleListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
