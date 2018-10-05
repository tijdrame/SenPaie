import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { MembreFamilleComponent } from './membre-famille.component';
import { MembreFamilleDetailComponent } from './membre-famille-detail.component';
import { MembreFamillePopupComponent } from './membre-famille-dialog.component';
import { MembreFamilleDeletePopupComponent } from './membre-famille-delete-dialog.component';

@Injectable()
export class MembreFamilleResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const membreFamilleRoute: Routes = [
    {
        path: 'membre-famille',
        component: MembreFamilleComponent,
        resolve: {
            'pagingParams': MembreFamilleResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN','ROLE_DG'],
            pageTitle: 'senPaieApp.membreFamille.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'membre-famille/:id',
        component: MembreFamilleDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_DG'],
            pageTitle: 'senPaieApp.membreFamille.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const membreFamillePopupRoute: Routes = [
    {
        path: 'membre-famille-new',
        component: MembreFamillePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_DG'],
            pageTitle: 'senPaieApp.membreFamille.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'membre-famille/:id/edit',
        component: MembreFamillePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.membreFamille.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'membre-famille/:id/delete',
        component: MembreFamilleDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.membreFamille.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
