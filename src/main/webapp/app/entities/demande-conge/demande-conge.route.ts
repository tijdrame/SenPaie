import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { DemandeCongeComponent } from './demande-conge.component';
import { DemandeCongeDetailComponent } from './demande-conge-detail.component';
import { DemandeCongePopupComponent } from './demande-conge-dialog.component';
import { DemandeCongeDeletePopupComponent } from './demande-conge-delete-dialog.component';

@Injectable()
export class DemandeCongeResolvePagingParams implements Resolve<any> {

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

export const demandeCongeRoute: Routes = [
    {
        path: 'demande-conge',
        component: DemandeCongeComponent,
        resolve: {
            'pagingParams': DemandeCongeResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_USER', 'ROLE_DG'],
            pageTitle: 'senPaieApp.demandeConge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'demande-conge/:id',
        component: DemandeCongeDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_USER', 'ROLE_DG'],
            pageTitle: 'senPaieApp.demandeConge.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const demandeCongePopupRoute: Routes = [
    {
        path: 'demande-conge-new',
        component: DemandeCongePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_USER', 'ROLE_DG'],
            pageTitle: 'senPaieApp.demandeConge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'demande-conge/:id/edit',
        component: DemandeCongePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_USER', 'ROLE_DG'],
            pageTitle: 'senPaieApp.demandeConge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'demande-conge/:id/delete',
        component: DemandeCongeDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_USER', 'ROLE_DG'],
            pageTitle: 'senPaieApp.demandeConge.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
